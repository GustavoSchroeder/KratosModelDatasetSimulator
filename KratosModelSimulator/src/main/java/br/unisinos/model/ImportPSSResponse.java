/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.pojo.Person;
import br.unisinos.pojo.Scales.PerceivedStressScale;
import br.unisinos.util.JPAUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gustavolazarottoschroeder
 * Studentlife Dataset https://www.kaggle.com/datasets/dartweichen/student-life
 */
public class ImportPSSResponse implements Serializable {

    public void importFiles() throws FileNotFoundException, IOException {
        String folder = "./src/main/java/files/survey_pss/PerceivedStressScale.csv";

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        try (BufferedReader br = new BufferedReader(new FileReader(folder))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values[1].trim().equalsIgnoreCase("type")) {
                    continue;
                }

                Integer startCountResponse = 0;

                String id = values[startCountResponse++].trim();
                Person person = em.find(Person.class, Long.parseLong(id.replace("u", "").trim()));
                String type = values[startCountResponse++].trim();
                String question1 = values[startCountResponse++].trim();
                String question2 = values[startCountResponse++].trim();
                String question3 = values[startCountResponse++].trim();
                String question4 = values[startCountResponse++].trim();
                String question5 = values[startCountResponse++].trim();
                String question6 = values[startCountResponse++].trim();
                String question7 = values[startCountResponse++].trim();
                String question8 = values[startCountResponse++].trim();
                String question9 = values[startCountResponse++].trim();
                String question10 = values[startCountResponse++].trim();

                Integer questionScore1 = transformResponse(question1, 1);
                Integer questionScore2 = transformResponse(question2, 2);
                Integer questionScore3 = transformResponse(question3, 3);
                Integer questionScore4 = transformResponse(question4, 4);
                Integer questionScore5 = transformResponse(question5, 5);
                Integer questionScore6 = transformResponse(question6, 6);
                Integer questionScore7 = transformResponse(question7, 7);
                Integer questionScore8 = transformResponse(question8, 8);
                Integer questionScore9 = transformResponse(question9, 9);
                Integer questionScore10 = transformResponse(question10, 10);

                Integer score = (questionScore1 + questionScore2 + questionScore3
                        + questionScore4 + questionScore5 + questionScore6
                        + questionScore7 + questionScore8 + questionScore9
                        + questionScore10);

//                ► Scores ranging from 0-13 would be considered low stress.
//                ► Scores ranging from 14-26 would be considered moderate stress.
//                ► Scores ranging from 27-40 would be considered high perceived stress
                String scoreAlias = "";
                if (score <= 13) {
                    scoreAlias = "Low Stress";
                } else if (score >= 14 && score <= 26) {
                    scoreAlias = "Moderate Stress";
                } else {
                    scoreAlias = "High Perceived Stress";
                }
                
                System.out.println(id + ";" + type + ";" + score + ";" + scoreAlias);
                
                PerceivedStressScale pss = new PerceivedStressScale(id, person, type, 
                        question1, question2, question3, question4, question5, 
                        question6, question7, question8, question9, question10, 
                        questionScore1, questionScore2, questionScore3, questionScore4, 
                        questionScore5, questionScore6, questionScore7, questionScore8, 
                        questionScore9, questionScore10, score, scoreAlias);
                em.merge(pss);
            }
        }

        deleteDataset();
        em.getTransaction().commit();
        em.close();
    }

    private Integer transformResponse(String response, Integer questionNumber) {
        //0 - never 1 - almost never 2 - sometimes 3 - fairly often 4 - very often
        //4, 5, 7, and 8.
        if (questionNumber == 4
                || questionNumber == 5
                || questionNumber == 7
                || questionNumber == 8) {
            switch (response) {
                case "Never":
                    return 4;
                case "Almost never":
                    return 3;
                case "Sometime":
                    return 2;
                case "Fairly often":
                    return 1;
                case "Very often":
                    return 0;
            }
        } else {
            switch (response) {
                case "Never":
                    return 0;
                case "Almost never":
                    return 1;
                case "Sometime":
                    return 2;
                case "Fairly often":
                    return 3;
                case "Very often":
                    return 4;
            }
        }
        return 0;
    }

     private void deleteDataset() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM PerceivedStressScale m");
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            em.close();
        }
    }
}
