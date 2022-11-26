/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.pojo.BasicSmartphoneUseInformation;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.PersonUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import javax.persistence.EntityManager;

/**
 *
 * @author gustavolazarottoschroeder
 * Data for: Batching Smartphone Notifications Can Improve Well-Being
 * https://data.mendeley.com/datasets/jxzsxzt2mz
 * https://www.sciencedirect.com/science/article/abs/pii/S0747563219302596?via%3Dihub
 */
public class ImportBasicSmartphoneUseInformation implements Serializable {

    private PersonUtil personUtil;

    public ImportBasicSmartphoneUseInformation() {
        this.personUtil = new PersonUtil();
    }

    public void importFiles() throws FileNotFoundException, IOException {
        String folder = "./src/main/java/files/notification/high_level/from_sav_data.csv";

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        try ( BufferedReader br = new BufferedReader(new FileReader(folder))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace("\"", "");
                String[] values = line.split(",");

                if (values[1].trim().equalsIgnoreCase("MergeKey")) {
                    continue;
                }

                Integer age;
                try {
                    age = Integer.parseInt(values[18]);
                } catch (Exception e) {
                    continue;
                }
                Integer ageGroup = this.personUtil.returnAgeGroup(age);
                Integer gender = Integer.parseInt(values[19]);
                Integer income = Integer.parseInt(values[20]);

                Double unlocks;
                try {
                    unlocks = Double.parseDouble(values[57]);
                } catch (Exception e) {
                    unlocks = null;
                }

                Double time;
                try {
                    time = Double.parseDouble(values[58]);
                } catch (Exception e) {
                    time = null;
                }

                Double checks;
                try {
                    checks = Double.parseDouble(values[59]);
                } catch (Exception e) {
                    checks = null;
                }

                Double appsOpen;
                try {
                    appsOpen = Double.parseDouble(values[60]);
                } catch (Exception e) {
                    appsOpen = null;
                }

                Double notificationDay;
                try {
                    notificationDay = Double.parseDouble(values[61]);
                } catch (Exception e) {
                    notificationDay = null;
                }

                BasicSmartphoneUseInformation bsui = new BasicSmartphoneUseInformation(
                        age, ageGroup, gender, income, unlocks, time, checks, appsOpen, notificationDay);
                em.merge(bsui);
            }

            em.getTransaction().commit();
            em.close();
        }
    }
}
