/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.pojo.EMA.StressEMA;
import br.unisinos.pojo.Person;
import br.unisinos.util.FileUtil;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.PersonUtil;
import br.unisinos.util.TimeUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class ImportStressEMA {

    private final PersonUtil personUtil;
    private final FileUtil fileUtil;
    private final TimeUtil timeUtil;

    public ImportStressEMA() {
        this.personUtil = new PersonUtil();
        this.fileUtil = new FileUtil();
        this.timeUtil = new TimeUtil();
    }

    public void importFiles() throws FileNotFoundException, IOException {
        String folder = "./src/main/java/files/ema_stress/";
        List<String> files;
        try {
            files = this.fileUtil.scanForFiles(folder);
        } catch (NullPointerException e) {
            System.out.println(e.getStackTrace());

            return;
        }

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        for (String fileName : files) {
            if (fileName.contains("json")) {
                System.out.println("File: " + fileName.split("_")[1].replace(".json", "").replace("u", ""));
                Long idPerson = Long.parseLong(fileName.split("_")[1].replace(".json", "").replace("u", ""));

                try ( BufferedReader br = new BufferedReader(new FileReader(folder + fileName))) {
                    String linha;

                    Integer stressLevel = null;
                    String location = null;
                    Date responseTime = null;
                    Person person = this.personUtil.findPerson(idPerson);

                    while ((linha = br.readLine()) != null) {
                        if (linha.contains("}")) {
                            if (null != stressLevel && null != person) {
                                System.out.println(idPerson + ";" + stressLevel + ";" + location + ";" + responseTime + ";" + fetchStressDescription(stressLevel));
                                StressEMA stressEMA = new StressEMA(stressLevel, fetchStressDescription(stressLevel), location, responseTime, person);
                                em.merge(stressEMA);
                            }

                            continue;
                        }

                        linha = linha.replace(",", "").replace("\"", "").trim();
                        String[] aux = linha.split(":");

                        if (aux[0].equalsIgnoreCase("null")) {
                            continue;
                        }

                        if (aux[0].equalsIgnoreCase("level")) {
                            stressLevel = Integer.parseInt(aux[1].trim());
                        }

                        if (aux[0].equalsIgnoreCase("location")) {
                            location = aux[1].replace(",", "").trim();
                        }

                        if (aux[0].equalsIgnoreCase("resp_time")) {
                            Long unixResponseTime = Long.parseLong(aux[1].trim());
                            responseTime = this.timeUtil.convertUnixTime(unixResponseTime);
                        }
                    }
                }
            }
        }
        em.getTransaction().commit();
        em.close();
    }

    private String fetchStressDescription(Integer level) {
        switch (level) {
            case 1:
                return "A little stressed";
            case 2:
                return "Definitely stressed";
            case 3:
                return "Stressed out";
            case 4:
                return "Feeling good";
            case 5:
                return "Feeling great";
        }
        return "-";
    }

}
