/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.pojo.EMA.SleepEMA;
import br.unisinos.pojo.Person;
import br.unisinos.util.FileUtil;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.PersonUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class ImportSleepEMA {

    private final PersonUtil personUtil;
    private final FileUtil fileUtil;

    public ImportSleepEMA() {
        this.personUtil = new PersonUtil();
        this.fileUtil = new FileUtil();
    }

    public void importFiles() throws FileNotFoundException, IOException {
        String folder = "./src/main/java/files/sleep/";
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
                Person person = this.personUtil.findPerson(idPerson);
                try ( BufferedReader br = new BufferedReader(new FileReader(folder + fileName))) {
                    String linha;
                    Integer hour = null;
                    String location = "";
                    Integer rate = null;
                    Long responseTime = null;
                    Integer social = null;
                    Date dataResponse = null;
                    while ((linha = br.readLine()) != null) {
                        if (linha.contains("}")) {
                            System.out.println(idPerson + ";" + hour + ";" + location + ";" + rate + ";" + responseTime + ";" + social);
                            SleepEMA emaSleep = new SleepEMA(
                                    hour,
                                    location,
                                    rate,
                                    dataResponse,
                                    social,
                                    person,
                                    dataResponse
                            );
                            em.merge(emaSleep);
                            continue;
                        }

                        linha = linha.replace(",", "").replace("\"", "").trim();
                        String[] aux = linha.split(":");
                        if (aux[0].equalsIgnoreCase("hour")) {
                            hour = Integer.parseInt(aux[1].trim());
                        }

                        if (aux[0].equalsIgnoreCase("location")) {
                            location = aux[1].replace(",", "").trim();
                        }

                        if (aux[0].equalsIgnoreCase("rate")) {
                            rate = Integer.parseInt(aux[1].replace(",", "").replace("\"", "").trim());
                        }

                        if (aux[0].equalsIgnoreCase("resp_time")) {
                            responseTime = Long.parseLong(aux[1].replace(",", "").trim());
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(new Date(responseTime * 1000));
                            dataResponse = calendar.getTime();
                        }

                        if (aux[0].equalsIgnoreCase("social")) {
                            social = Integer.parseInt(aux[1].replace(",", "").trim());
                        }
                    }
                }
            }
        }
        deleteDataset();
        em.getTransaction().commit();
        em.close();
    }

    private void deleteDataset() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM SleepEMA m");
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            em.close();
        }
    }

}
