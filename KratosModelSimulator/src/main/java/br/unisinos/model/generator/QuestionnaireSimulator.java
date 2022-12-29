package br.unisinos.model.generator;

import br.unisinos.pojo.ContextHistorySmartphoneUse;
import br.unisinos.pojo.Scales.DepressionAnxietyScale;
import br.unisinos.pojo.Scales.NomophobiaQuestionnaire;
import br.unisinos.pojo.Scales.SmartphoneAddictionScale;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.PersonUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author gustavolazarottoschroeder
 */
public class QuestionnaireSimulator {

    private PersonUtil personUtil;

    public QuestionnaireSimulator() {
        this.personUtil = new PersonUtil();
    }

    public DepressionAnxietyScale fetchDASS21(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM DepressionAnxietyScale i WHERE i.person.id = :personId");
        query.setParameter("personId", id);
        try {
            return (DepressionAnxietyScale) query.getResultList().get(0);
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Map<Boolean, Map<Long, SmartphoneAddictionScale>> fetchSAS() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM SmartphoneAddictionScale i");
        List<SmartphoneAddictionScale> listSAS = query.getResultList();

        Map<Boolean, Map<Long, SmartphoneAddictionScale>> output = new HashMap<>();
        for (SmartphoneAddictionScale sas : listSAS) {
            Boolean addicted = sas.getResult() == 1;
            if (null == output.get(addicted)) {
                output.put(addicted, new HashMap<>());
            }
            output.get(addicted).put(sas.getId(), sas);
        }
        em.close();
        return output;
    }

    public Map<String, Map<Long, NomophobiaQuestionnaire>> fetchNomophobia() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM NomophobiaQuestionnaire i");
        List<NomophobiaQuestionnaire> listNomophobia = query.getResultList();

        Map<String, Map<Long, NomophobiaQuestionnaire>> output = new HashMap<>();
        for (NomophobiaQuestionnaire nomo : listNomophobia) {
            if (null == output.get(nomo.getCategory())) {
                output.put(nomo.getCategory(), new HashMap<>());
            }
            output.get(nomo.getCategory()).put(nomo.getId(), nomo);
        }
        em.close();
        return output;
    }

    public void fetchPeopleEligibleNomophobia() {
        Double[] percentuais = fetchQuartis();
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT distinct(i.person.id), "
                + "avg(i.batteryLevel), "
                + "avg(i.applicationUseTime), "
                + "avg(i.quantityNotifications), "
                + "avg(i.anxietyScore), "
                + "avg(i.stressScore), "
                + "i.id FROM ContextHistorySmartphoneUse i group by i.person.id, i.id order by avg(i.applicationUseTime) desc, avg(i.batteryLevel) desc, avg(i.quantityNotifications) desc, avg(i.anxietyScore) desc, avg(i.stressScore) desc");
        List<Object[]> auxList = query.getResultList();

        List<Long> idsOrdered = new ArrayList<>();

        Map<Long, List<Object[]>> personSet = new HashMap<>();
        for (Object[] objects : auxList) {

            if (!idsOrdered.contains((Long) objects[0])) {
                idsOrdered.add((Long) objects[0]);
            }

            if (null == personSet.get((Long) objects[0])) {
                personSet.put((Long) objects[0], new ArrayList<>());
            }
            personSet.get((Long) objects[0]).add(objects);
        }

        em.close();

        em = JPAUtil.getEntityManager();

        Integer limiteSevere = ((Double) (personSet.size() * percentuais[2])).intValue();
        Integer limiteModerate = ((Double) (personSet.size() * percentuais[1])).intValue();

        Integer qtdeSevere = 0;
        Integer qtdeModerate = 0;

        Map<String, List<NomophobiaQuestionnaire>> dictionaryNomo = fetchNomophobiQuest();
        List<Long> idsDraft = new ArrayList<>();
        Random rand = new Random();

        em.getTransaction().begin();
        for (Long key : idsOrdered) {
            List<Object[]> value = personSet.get(key);
            String nomophobia = "";
            NomophobiaQuestionnaire quest = null;

            if (limiteSevere.intValue() > qtdeSevere++) {
                nomophobia = "Severe nomophobia";
            } else if (limiteModerate.intValue() > qtdeModerate++) {
                nomophobia = "Moderate level of nomophobia";
            } else {
                nomophobia = "Mild level of nomophobia";
            }

            List<NomophobiaQuestionnaire> list = dictionaryNomo.get(nomophobia);
            Integer n = null;
            Long id = -1L;
            do {
                n = rand.nextInt(list.size());
                quest = list.get(n);
                id = quest.getId();
            } while (idsDraft.contains(id));

            for (Object[] obj : value) {
                ContextHistorySmartphoneUse context = em.find(ContextHistorySmartphoneUse.class, (Long) obj[6]);
                context.setNomophobiaLevel(quest.getCategory());
                context.setTotalResultNomophobia(quest.getNmpqTotal());
                em.merge(context);

                System.out.println(obj[0] + ";" + obj[1] + ";" + obj[2] + ";" + obj[3] + ";" + obj[4] + ";" + obj[5] + ";" + nomophobia + ";" + id);
            }

        }
        em.getTransaction().commit();
        em.close();
    }

    private Double[] fetchQuartis() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i.category FROM NomophobiaQuestionnaire i");
        List<String> category = query.getResultList();

        Double[] quantities = {0.0, 0.0, 0.0, 0.0};
        for (String quantity : category) {
            switch (quantity) {
                case "Mild level of nomophobia":
                    quantities[0] = quantities[0] + 1;
                    break;
                case "Moderate level of nomophobia":
                    quantities[1] = quantities[1] + 1;
                    break;
                case "Severe nomophobia":
                    quantities[2] = quantities[2] + 1;
                    break;
            }
        }

        Double total = quantities[0] + quantities[1] + quantities[2] + quantities[3];
        quantities[0] = quantities[0] / total;
        quantities[1] = quantities[1] / total;
        quantities[2] = quantities[2] / total;
        quantities[3] = quantities[3] / total;
        em.close();

        return quantities;

    }

    private Map<String, List<NomophobiaQuestionnaire>> fetchNomophobiQuest() {
        Map<String, List<NomophobiaQuestionnaire>> outputMap = new HashMap<>();

        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM NomophobiaQuestionnaire i");
        List<NomophobiaQuestionnaire> nomophobiaQuest = query.getResultList();

        for (NomophobiaQuestionnaire nomo : nomophobiaQuest) {
            if (null == outputMap.get(nomo.getCategory())) {
                outputMap.put(nomo.getCategory(), new ArrayList<>());
            }
            outputMap.get(nomo.getCategory()).add(nomo);
        }

        return outputMap;
    }

    public void fetchPeopleEligibleSAS() {
        Double[] percentuais = fetchQuartisSAS();
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT "
                + "distinct(i.person.id), "
                + "avg(i.applicationUseTime), "
                + "avg(i.quantityNotifications), "
                + "avg(i.anxietyScore), "
                + "avg(i.stressScore), "
                + "avg(i.depressionScore), "
                + "i.id "
                + "FROM ContextHistorySmartphoneUse i "
                + "group by i.person.id, i.id "
                + "order by avg(i.applicationUseTime) desc, "
                + "avg(i.quantityNotifications) desc, "
                + "avg(i.anxietyScore) desc, "
                + "avg(i.stressScore) desc, "
                + "avg(i.depressionScore) desc");
        List<Object[]> listSAS = query.getResultList();
        em.close();

        List<Long> idsOrdered = new ArrayList<>();

        Map<Long, List<Object[]>> personSet = new HashMap<>();
        for (Object[] objects : listSAS) {

            if (!idsOrdered.contains((Long) objects[0])) {
                idsOrdered.add((Long) objects[0]);
            }

            if (null == personSet.get((Long) objects[0])) {
                personSet.put((Long) objects[0], new ArrayList<>());
            }
            personSet.get((Long) objects[0]).add(objects);
        }

        em = JPAUtil.getEntityManager();

        Integer limiteAddicted = ((Double) (personSet.size() * percentuais[0])).intValue();

        Integer addicted = 0;

        Map<Integer, Map<String, List<SmartphoneAddictionScale>>> dictionarySAS = fetchSmartphoneAddictionScale();
        List<Long> idsDraft = new ArrayList<>();
        Random rand = new Random();

        em.getTransaction().begin();
        for (Long key : idsOrdered) {
            List<Object[]> value = personSet.get(key);

            String gender = "";
            gender = this.personUtil.fetchGender(key);
            switch (gender) {
                case "Female":
                    gender = "P";
                    break;
                case "Male":
                    gender = "L";
                    break;
                case "Other":
                    gender = "P";
                    break;
            }

            Integer addictedSmartphone = 0;
            SmartphoneAddictionScale quest = null;

            if (limiteAddicted.intValue() > addicted++) {
                addictedSmartphone = 1;
            } else {
                addictedSmartphone = 0;
            }

            List<SmartphoneAddictionScale> list = dictionarySAS.get(addictedSmartphone).get(gender);
            Integer n = null;
            Long id = -1L;
            do {
                n = rand.nextInt(list.size());
                quest = list.get(n);
                id = quest.getId();
            } while (idsDraft.contains(id));

            for (Object[] obj : value) {
                ContextHistorySmartphoneUse context = em.find(ContextHistorySmartphoneUse.class, (Long) obj[6]);
                context.setSmartphoneAddicted((addictedSmartphone == 1));
                context.setTotalResultSAS(quest.getTotal());
                em.merge(context);

                //System.out.println(obj[0] + ";" + obj[1] + ";" + obj[2] + ";" + obj[3] + ";" + obj[4] + ";" + obj[5] + ";" + obj[6] + ";" + addictedSmartphone + ";" + id);
                System.out.println(obj[0] + ";" + obj[6] + ";" + addictedSmartphone);
            }
        }

        em.getTransaction().commit();
        em.close();
    }

    private Double[] fetchQuartisSAS() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i.result FROM SmartphoneAddictionScale i");
        List<Integer> category = query.getResultList();

        Double[] quantities = {0.0, 0.0};
        for (Integer quantity : category) {
            switch (quantity) {
                case 0:
                    quantities[0] = quantities[0] + 1;
                    break;
                case 1:
                    quantities[1] = quantities[1] + 1;
                    break;
            }
        }

        Double total = quantities[0] + quantities[1];
        quantities[0] = quantities[0] / total;
        quantities[1] = quantities[1] / total;
        em.close();

        return quantities;

    }

    private Map<Integer, Map<String, List<SmartphoneAddictionScale>>> fetchSmartphoneAddictionScale() {
        Map<Integer, Map<String, List<SmartphoneAddictionScale>>> outputMap = new HashMap<>();

        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM SmartphoneAddictionScale i");
        List<SmartphoneAddictionScale> sasScale = query.getResultList();

        for (SmartphoneAddictionScale sas : sasScale) {
            if (null == outputMap.get(sas.getResult())) {
                outputMap.put(sas.getResult(), new HashMap<>());
            }

            if (null == outputMap.get(sas.getResult()).get(sas.getGender())) {
                outputMap.get(sas.getResult()).put(sas.getGender(), new ArrayList<>());
            }

            outputMap.get(sas.getResult()).get(sas.getGender()).add(sas);
        }

        return outputMap;
    }
}
