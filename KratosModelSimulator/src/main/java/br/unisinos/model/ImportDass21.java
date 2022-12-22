/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.pojo.Person;
import br.unisinos.pojo.Scales.DepressionAnxietyScale;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.PersonUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class ImportDass21 {

    private final PersonUtil personUtil;

    public ImportDass21() {
        this.personUtil = new PersonUtil();
    }

    public void importFiles() throws FileNotFoundException, IOException {
        String folder = "./src/main/java/files/survey_dass21/data.csv";

        List<DepressionAnxietyScale> listDass21 = new ArrayList<>();
        
        deleteDataset();

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        Integer counter = 0;
        try ( BufferedReader br = new BufferedReader(new FileReader(folder))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace("\"", "");
                String[] values = line.split(";");

                if (values[1].trim().equalsIgnoreCase("Q1I")) {
                    continue;
                }

                Integer age = Integer.parseInt(values[161].trim());

                if (age > 90) {
                    System.out.println(age);
                    continue;
                }

                Integer gender = Integer.parseInt(values[159].trim());
                Integer education = Integer.parseInt(values[157].trim());

                Integer question1 = Integer.parseInt(values[63].trim()); //I found it hard to wind down.
                Integer question2 = Integer.parseInt(values[3].trim()); //I was aware of dryness of my mouth.
                Integer question3 = Integer.parseInt(values[6].trim()); //I couldn't seem to experience any positive feeling at all.
                Integer question4 = Integer.parseInt(values[9].trim()); //I experienced breathing difficulty (eg, excessively rapid breathing, breathlessness in the absence of physical exertion).
                Integer question5 = Integer.parseInt(values[123].trim()); //I found it difficult to work up the initiative to do things.
                Integer question6 = Integer.parseInt(values[15].trim()); //I tended to over-react to situations.
                Integer question7 = Integer.parseInt(values[120].trim()); //I experienced trembling (eg, in the hands).
                Integer question8 = Integer.parseInt(values[33].trim()); //I felt that I was using a lot of nervous energy.
                Integer question9 = Integer.parseInt(values[117].trim()); //I was worried about situations in which I might panic and make a fool of myself.
                Integer question10 = Integer.parseInt(values[27].trim()); //I felt that I had nothing to look forward to.
                Integer question11 = Integer.parseInt(values[114].trim()); //I found myself getting agitated.
                Integer question12 = Integer.parseInt(values[21].trim()); //I found it difficult to relax.
                Integer question13 = Integer.parseInt(values[75].trim()); //I felt down-hearted and blue.
                Integer question14 = Integer.parseInt(values[102].trim()); //I was intolerant of anything that kept me from getting on with what I was doing.
                Integer question15 = Integer.parseInt(values[81].trim()); //I felt I was close to panic.
                Integer question16 = Integer.parseInt(values[90].trim()); //I was unable to become enthusiastic about anything.
                Integer question17 = Integer.parseInt(values[48].trim()); //I felt I wasn&#39;t worth much as a person.
                Integer question18 = Integer.parseInt(values[51].trim()); //I felt that I was rather touchy.
                Integer question19 = Integer.parseInt(values[72].trim()); //I was aware of the action of my heart in the absence of physical exertion (eg, sense of heart rate increase, heart missing a beat).
                Integer question20 = Integer.parseInt(values[57].trim()); //I felt scared without any good reason.
                Integer question21 = Integer.parseInt(values[111].trim()); //I felt that life was meaningless.

                //A (Anxiety) Q2, 4, 7, 9, 15, 19, 20
                Integer anxietyScore = question2 + question4
                        + question7 + question9 + question15
                        + question19 + question20;

                //S (Stress) Q1, 6, 8, 11, 12, 14, 18
                Integer stressScore = question1 + question6
                        + question8 + question11 + question12
                        + question14 + question18;

                //D (Depression) Q3, 5, 10, 13, 16, 17, 21
                Integer depressionScore = question3 + question5 + question10
                        + question13 + question16 + question17 + question21;

                String stressStatus = "";
                String anxietyStatus = "";
                String depressionStatus = "";

                if (anxietyScore <= 10) {
                    anxietyStatus = "Normal";
                } else if (anxietyScore >= 11 && anxietyScore <= 18) {
                    anxietyStatus = "Mild";
                } else if (anxietyScore >= 19 && anxietyScore <= 26) {
                    anxietyStatus = "Moderate";
                } else if (anxietyScore >= 27 && anxietyScore <= 34) {
                    anxietyStatus = "Severe";
                } else if (anxietyScore >= 35 && anxietyScore <= 42) {
                    anxietyStatus = "Extremely Severe";
                }

                if (stressScore <= 6) {
                    stressStatus = "Normal";
                } else if (stressScore >= 7 && stressScore <= 9) {
                    stressStatus = "Mild";
                } else if (stressScore >= 10 && stressScore <= 14) {
                    stressStatus = "Moderate";
                } else if (stressScore >= 15 && stressScore <= 19) {
                    stressStatus = "Severe";
                } else if (stressScore >= 20 && stressScore <= 42) {
                    stressStatus = "Extremely Severe";
                }

                if (depressionScore <= 9) {
                    depressionStatus = "Normal";
                } else if (depressionScore >= 10 && depressionScore <= 12) {
                    depressionStatus = "Mild";
                } else if (depressionScore >= 13 && depressionScore <= 20) {
                    depressionStatus = "Moderate";
                } else if (depressionScore >= 21 && depressionScore <= 27) {
                    depressionStatus = "Severe";
                } else if (depressionScore >= 28 && depressionScore <= 42) {
                    depressionStatus = "Extremely Severe";
                }

                DepressionAnxietyScale dass = new DepressionAnxietyScale(null,
                        question1, question2, question3, question4, question5,
                        question6, question7, question8, question9, question10,
                        question11, question12, question13, question14, question15,
                        question16, question17, question18, question19, question20,
                        question21, stressScore, anxietyScore, depressionScore, stressStatus,
                        anxietyStatus, depressionStatus, age, gender, education);
                em.persist(dass);
                listDass21.add(dass);

            }
        }
        em.getTransaction().commit();

        em.getTransaction().begin();

        List<Long> idsPerson = this.personUtil.fetchListIds();
        Integer maxUser = idsPerson.size();
        List<Integer> pastNumbersUser = new ArrayList<>();

        Integer maxDass = listDass21.size() - 1;
        List<Integer> pastNumbersDass = new ArrayList<>();

        Random rand = new Random();

        //O usuário é randomizado
        for (int i = 0; i < listDass21.size(); i++) {

            if (pastNumbersUser.size() == idsPerson.size()) {
                break;
            }

            Integer n;
            do {
                n = rand.nextInt(maxUser);
            } while (pastNumbersUser.contains(n));

            pastNumbersUser.add(n);
            Long idUser = idsPerson.get(n);

            Integer nDass;
            do {
                nDass = rand.nextInt(maxDass);
            } while (pastNumbersDass.contains(nDass));

            pastNumbersDass.add(nDass);
            DepressionAnxietyScale dass = listDass21.get(nDass);

            Person p = this.personUtil.findPerson(idUser, Boolean.FALSE);
            p.setAge(dass.getAge());

            if (null != dass.getGender()) {
                switch (dass.getGender()) {
                    case 1:
                        p.setGender("Male");
                        break;
                    case 2:
                        p.setGender("Female");
                        break;
                    case 3:
                        System.out.println("Other");
                        p.setGender("Other");
                        break;
                    default:
                        break;
                }
            }

            p.setEducationalLevel(dass.getEducation());

            em.merge(p);

            dass.setPerson(p);
            em.merge(dass);
        }

        em.getTransaction().commit();
        em.close();
    }

    private void deleteDataset() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM DepressionAnxietyScale m");
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            em.close();
        }
    }
}
