/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.pojo.Scales.SmartphoneAddictionScale;
import br.unisinos.util.JPAUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import javax.persistence.EntityManager;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class ImportSASResponse implements Serializable {

    public void importFiles() throws FileNotFoundException, IOException {
        String folder = "./src/main/java/files/survey_sas/Raw_Data.csv";

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        try (BufferedReader br = new BufferedReader(new FileReader(folder))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                if (values[1].trim().equalsIgnoreCase("GENDER")) {
                    continue;
                }

                Integer startCountResponse = 0;
                Long id = Long.parseLong(values[startCountResponse++].trim());
                String tpPessoa = values[startCountResponse++].trim();
                Integer resp1 = Integer.parseInt(values[startCountResponse++].trim());
                Integer resp2 = Integer.parseInt(values[startCountResponse++].trim());
                Integer resp3 = Integer.parseInt(values[startCountResponse++].trim());
                Integer resp4 = Integer.parseInt(values[startCountResponse++].trim());
                Integer resp5 = Integer.parseInt(values[startCountResponse++].trim());
                Integer resp6 = Integer.parseInt(values[startCountResponse++].trim());
                Integer resp7 = Integer.parseInt(values[startCountResponse++].trim());
                Integer resp8 = Integer.parseInt(values[startCountResponse++].trim());
                Integer resp9 = Integer.parseInt(values[startCountResponse++].trim());
                Integer resp10 = Integer.parseInt(values[startCountResponse++].trim());
                Integer total = Integer.parseInt(values[startCountResponse++].trim());
                Integer result = Integer.parseInt(values[startCountResponse++].trim());

                System.out.println(id + ";"
                        + tpPessoa + ";"
                        + ";" + tpPessoa
                        + ";" + resp1
                        + ";" + resp2
                        + ";" + resp3
                        + ";" + resp4
                        + ";" + resp5
                        + ";" + resp6
                        + ";" + resp7
                        + ";" + resp8
                        + ";" + resp9
                        + ";" + resp10
                        + ";" + total
                        + ";" + result
                );

                SmartphoneAddictionScale sas = new SmartphoneAddictionScale(id,
                        tpPessoa, resp1, resp2, resp3, resp4, resp5, resp6,
                        resp7, resp8, resp9, resp10, total, result);
                em.merge(sas);
            }
        }

        em.getTransaction().commit();
        em.close();
    }
}
