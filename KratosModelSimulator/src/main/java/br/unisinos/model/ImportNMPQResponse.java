/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.pojo.ContextInformation.ReportedTimeSpentSmartphone;
import br.unisinos.pojo.Person;
import br.unisinos.pojo.Scales.NomophobiaQuestionnaire;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.PersonUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gustavolazarottoschroeder The relationship between the nomophobic
 * levels of higher education students in Ghana and academic achievement
 * https://figshare.com/articles/dataset/Descriptive_statistics_of_students_scores_with_the_NMP-Q_scale_i_N_i_670_/14793089/1
 * https://figshare.com/articles/dataset/NMPQ_data_sav/21507951
 */
public class ImportNMPQResponse implements Serializable {

    private final PersonUtil personUtil;

    public ImportNMPQResponse() {
        this.personUtil = new PersonUtil();
    }

    public void importFiles() throws FileNotFoundException, IOException {
        String folder = "./src/main/java/files/survey_nmpq/from_sav_data.csv";

        List<NomophobiaQuestionnaire> nmpqList = new ArrayList<>();
        Map<Integer, Object[]> dictionaryPersonInfo = new HashMap<>();

        Integer counter = 0;
        try ( BufferedReader br = new BufferedReader(new FileReader(folder))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace("\"", "");
                String[] values = line.split(",");

                if (values[1].trim().equalsIgnoreCase("Gender")) {
                    continue;
                }

                Integer age = Integer.parseInt(values[0].trim());
                Integer gender = Integer.parseInt(values[1].trim());
                Integer relationshipStatus = Integer.parseInt(values[2].trim());
                Double dailyPhoneUseTime = null;
                try {
                    dailyPhoneUseTime = Double.parseDouble(values[10].trim());
                } catch (Exception e) {
                }
                Object[] personObjArray = {age, gender, relationshipStatus, dailyPhoneUseTime};

                Integer startCountResponse = 15;

                Integer response1 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response2 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response3 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response4 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response5 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response6 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response7 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response8 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response9 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response10 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response11 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response12 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response13 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response14 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response15 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response16 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response17 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response18 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response19 = Integer.parseInt(values[startCountResponse++].trim());
                Integer response20 = Integer.parseInt(values[startCountResponse++].trim());

                Integer nmpqTotal = Integer.parseInt(values[71].trim());
                Integer numTotal = response1
                        + response2
                        + response3
                        + response4
                        + response5
                        + response6
                        + response7
                        + response8
                        + response9
                        + response10
                        + response11
                        + response12
                        + response13
                        + response14
                        + response15
                        + response16
                        + response17
                        + response18
                        + response19
                        + response20;

                String category;
                if (numTotal <= 20) {
                    category = "Absence of nomophobia";
                } else if (numTotal > 20 && numTotal < 60) {
                    category = "Mild level of nomophobia";
                } else if (numTotal >= 60 && numTotal <= 99) {
                    category = "Moderate level of nomophobia";
                } else {
                    category = "Severe nomophobia";
                }

                NomophobiaQuestionnaire nmpq = new NomophobiaQuestionnaire(null, numTotal, response1, response2,
                        response3, response4, response5, response6, response7, response8, response9, response10,
                        response11, response12, response13, response14, response15, response16, response17,
                        response18, response19, response20, category);
                nmpqList.add(nmpq);
                dictionaryPersonInfo.put(counter++, personObjArray);
            }
        }

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        List<Long> idsPerson = this.personUtil.fetchListIds();
        Integer max = idsPerson.size();

        List<Integer> pastNumbers = new ArrayList<>();

        Random rand = new Random();

        //O usuário é randomizado
        for (int i = 0; i < nmpqList.size(); i++) {

            if (pastNumbers.size() == idsPerson.size()) {
                break;
            }

            Integer n;
            do {
                n = rand.nextInt(max);
            } while (pastNumbers.contains(n));

            pastNumbers.add(n);
            Long idUser = idsPerson.get(n);

            Person p = this.personUtil.findPerson(idUser, Boolean.FALSE);

            Object[] infoP = dictionaryPersonInfo.get(i);

            ReportedTimeSpentSmartphone rtsp = new ReportedTimeSpentSmartphone(idUser, p, (Double) infoP[3]);

            em.merge(rtsp);

            nmpqList.get(i).setPerson(p);
            em.merge(nmpqList.get(i));
        }
        
        deleteDataset();
        em.getTransaction().commit();
        em.close();
    }

    private void deleteDataset() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM NomophobiaQuestionnaire m");
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            em.close();
        }
    }
}
