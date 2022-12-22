package br.unisinos.model.generator;

import br.unisinos.pojo.Scales.DepressionAnxietyScale;
import br.unisinos.pojo.Scales.NomophobiaQuestionnaire;
import br.unisinos.pojo.Scales.SmartphoneAddictionScale;
import br.unisinos.util.JPAUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        for (NomophobiaQuestionnaire sas : listNomophobia) {
            if (null == output.get(sas.getCategory())) {
                output.put(sas.getCategory(), new HashMap<>());
            }
            output.get(sas.getCategory()).put(sas.getId(), sas);
        }
        em.close();
        return output;
    }
}
