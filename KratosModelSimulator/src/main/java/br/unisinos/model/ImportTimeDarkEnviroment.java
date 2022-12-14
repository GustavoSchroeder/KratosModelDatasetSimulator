/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.util.PersonUtil;
import br.unisinos.pojo.Person;
import br.unisinos.pojo.ContextInformation.TimeDarkEnvironment;
import br.unisinos.util.FileUtil;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.TimeUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class ImportTimeDarkEnviroment implements Serializable {

    private final PersonUtil personUtil;
    private final TimeUtil timeUtil;
    private final FileUtil fileUtil;

    public ImportTimeDarkEnviroment() {
        this.personUtil = new PersonUtil();
        this.timeUtil = new TimeUtil();
        this.fileUtil = new FileUtil();
    }

    public void importFiles() throws FileNotFoundException, IOException {
        String folder = "./src/main/java/files/dark/";
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
            if (fileName.contains("csv")) {
                System.out.println("File: " + fileName.split("_")[1].replace(".csv", "").replace("u", ""));
                Long idPerson = Long.parseLong(fileName.split("_")[1].replace(".csv", "").replace("u", ""));

                try ( BufferedReader br = new BufferedReader(new FileReader(folder + fileName))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");

                        if (values[0].equalsIgnoreCase("start")) {
                            continue;
                        }

                        Long timeInicial = Long.parseLong(values[0].trim());
                        Long timeFinal = Long.parseLong(values[1].trim());

                        Calendar initialDate = Calendar.getInstance();
                        initialDate.setTime(new Date(timeInicial * 1000));
                        Calendar finalDate = Calendar.getInstance();
                        finalDate.setTime(new Date(timeFinal * 1000));
                        Date diffHours = this.timeUtil.diffHours(initialDate.getTime(), finalDate.getTime());

                        Person person = this.personUtil.findPerson(idPerson);
                        TimeDarkEnvironment tde = new TimeDarkEnvironment(
                                person,
                                initialDate.getTime(),
                                finalDate.getTime(),
                                diffHours,
                                initialDate.getTime(),
                                finalDate.getTime());
                        em.merge(tde);

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
            Query query = em.createQuery("DELETE FROM TimeDarkEnvironment m");
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            em.close();
        }
    }
}
