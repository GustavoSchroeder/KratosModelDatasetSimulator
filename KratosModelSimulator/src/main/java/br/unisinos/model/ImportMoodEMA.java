/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.pojo.EMA.MoodEMA;
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
public class ImportMoodEMA {

    private final PersonUtil personUtil;
    private final FileUtil fileUtil;
    private final TimeUtil timeUtil;

    public ImportMoodEMA() {
        this.personUtil = new PersonUtil();
        this.fileUtil = new FileUtil();
        this.timeUtil = new TimeUtil();
    }

    public void importFiles() throws FileNotFoundException, IOException {
        String folder = "./src/main/java/files/ema_mood/";
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

                    Integer happy = null;
                    Integer happyOrNot = null;
                    Integer sad = null;
                    Integer sadOrNot = null;
                    String location = null;
                    Date responseTime = null;
                    Person person = this.personUtil.findPerson(idPerson);

                    while ((linha = br.readLine()) != null) {
                        if (linha.contains("}")) {

                            String happyDesc = happy == 1 ? "Yes" : "No";
                            String happyOrNotDesc = fetchDescription(happy);
                            String sadDesc = sad == 1 ? "Yes" : "No";
                            String sadOrNotDesc = fetchDescription(sad);

                            MoodEMA mood = new MoodEMA(person, happy, happyOrNot, sad, sadOrNot, location, responseTime, happyDesc, happyOrNotDesc, sadDesc, sadOrNotDesc);
                            em.merge(mood);

                            continue;
                        }

                        linha = linha.replace(",", "").replace("\"", "").trim();
                        String[] aux = linha.split(":");

                        if (aux[0].equalsIgnoreCase("null")) {
                            continue;
                        }

                        try {
                            if (aux[0].equalsIgnoreCase("happy")) {
                                happy = Integer.parseInt(aux[1].trim());
                            }
                        } catch (Exception e) {

                        }

                        try {
                            if (aux[0].equalsIgnoreCase("happyornot")) {
                                happyOrNot = Integer.parseInt(aux[1].trim());
                            }
                        } catch (Exception e) {

                        }

                        try {
                            if (aux[0].equalsIgnoreCase("sad")) {
                                sad = Integer.parseInt(aux[1].trim());
                            }
                        } catch (Exception e) {

                        }

                        try {
                            if (aux[0].equalsIgnoreCase("sadornot")) {
                                sadOrNot = Integer.parseInt(aux[1].trim());
                            }
                        } catch (Exception e) {
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

    private String fetchDescription(Integer level) {
        //]a little bit, [2]somewhat, [3]very much, [4]extremely
        switch (level) {
            case 1:
                return "A little bit";
            case 2:
                return "Somewhat";
            case 3:
                return "Very Much";
            case 4:
                return "Extremely";
        }
        return "";
    }

}
