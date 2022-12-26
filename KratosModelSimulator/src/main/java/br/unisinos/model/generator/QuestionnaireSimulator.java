package br.unisinos.model.generator;

import br.unisinos.pojo.ContextHistorySmartphoneUse;
import br.unisinos.pojo.Scales.DepressionAnxietyScale;
import br.unisinos.pojo.Scales.NomophobiaQuestionnaire;
import br.unisinos.pojo.Scales.SmartphoneAddictionScale;
import br.unisinos.util.JPAUtil;
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

        em.close();

        em = JPAUtil.getEntityManager();

        Integer limiteSevere = ((Double) (auxList.size() * percentuais[2])).intValue();
        Integer limiteModerate = ((Double) (auxList.size() * percentuais[1])).intValue();

        Integer qtdeSevere = 0;
        Integer qtdeModerate = 0;

        Map<String, List<NomophobiaQuestionnaire>> dictionaryNomo = fetchNomophobiQuest();
        List<Long> idsDraft = new ArrayList<>();
        Random rand = new Random();

        em.getTransaction().begin();
        for (Object[] obj : auxList) {
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

            ContextHistorySmartphoneUse context = em.find(ContextHistorySmartphoneUse.class, (Long) obj[6]);
            context.setNomophobiaLevel(quest.getCategory());
            context.setTotalResultNomophobia(quest.getNmpqTotal());
            em.merge(context);

            System.out.println(obj[0] + ";" + obj[1] + ";" + obj[2] + ";" + obj[3] + ";" + obj[4] + ";" + obj[5] + ";" + nomophobia + ";" + id);
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
}
