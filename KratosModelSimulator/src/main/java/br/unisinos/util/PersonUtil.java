/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.util;

import br.unisinos.pojo.ApplicationCategory;
import br.unisinos.pojo.Person;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class PersonUtil {

    public Person findPerson(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Person person = em.find(Person.class, id);
            if (null == person) {
                return createNewSimplePerson(id);
            }
            return person;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Person findPerson(Long id, Boolean createNewPerson) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Person person = em.find(Person.class, id);
            if (null == person && createNewPerson) {
                return createNewSimplePerson(id);
            }
            return person;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Person createNewSimplePerson(Long id) {

        Person p = new Person(id, "", "");
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        em.close();

        return p;
    }

    public List<Long> fetchListIds() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i.id FROM Person i ORDER BY i.id");
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public Integer returnAgeGroup(Integer age) {
        if (age < 10) {
            return 1;
        } else if (age >= 10 && age < 20) {
            return 2;
        } else if (age >= 20 && age < 30) {
            return 3;
        } else if (age >= 30 && age < 40) {
            return 4;
        } else if (age >= 40 && age < 50) {
            return 5;
        } else if (age >= 50 && age < 60) {
            return 6;
        } else if (age >= 60 && age < 70) {
            return 7;
        } else if (age >= 70 && age < 80) {
            return 8;
        } else if (age >= 80 && age < 90) {
            return 9;
        }
        return 10;
    }

    public Map<Long, Person> findPersonList() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM Person i");
        List<Person> listP = query.getResultList();
        Map<Long, Person> dictionary = new HashMap<>();
        for (Person person : listP) {
            dictionary.put(person.getId(), person);
        }
        em.close();
        return dictionary;
    }

    public List<Long> findPersonIdList() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i.id FROM Person i ORDER BY i.id");
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public List<Long> fetchUserIdsBasedStress(EntityManager em) {
        Query query = em.createQuery("SELECT i.person.id, i.stressLevel FROM StressEMA i");
        List<Object[]> queryOutput = query.getResultList();

        List<Object[]> listCorrected = new ArrayList<>();
        for (Object[] obj : queryOutput) {
            Integer value = (Integer) obj[1];
            if (value == 3) {
                value = 5;
            }
            if (value == 2) {
                value = 4;
            }
            if (value == 1) {
                value = 3;
            }
            if (value == 4) {
                value = 2;
            }
            if (value == 5) {
                value = 1;
            }
            obj[1] = value;
            listCorrected.add(obj);
        }

        Map<Long, Integer> totalStress = new HashMap<>();
        Map<Long, Integer> totalResponses = new HashMap<>();
        for (Object[] obj : listCorrected) {
            Long idPerson = (Long) obj[0];
            if (null == totalStress.get(idPerson)) {
                totalStress.put(idPerson, 0);
            }
            if (null == totalResponses.get(idPerson)) {
                totalResponses.put(idPerson, 0);
            }

            totalStress.put(idPerson, totalStress.get(idPerson) + (Integer) obj[1]);
            totalResponses.put(idPerson, totalResponses.get(idPerson) + 1);
        }

        Map<Long, Double> stressAvg = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : totalStress.entrySet()) {
            Long key = entry.getKey();
            Integer val = entry.getValue();
            Integer quantity = totalResponses.get(key);
            stressAvg.put(key, (val / quantity.doubleValue()));
        }

        stressAvg = sortByValue(stressAvg);

//        for (Map.Entry<Long, Double> entry : stressAvg.entrySet()) {
//            Long key = entry.getKey();
//            Double val = entry.getValue();
//            System.out.println(key + ";" + val);
//        }
        List<Long> personIdListComplete = findPersonIdList();

        List<Long> personIdList = new ArrayList(stressAvg.keySet());
        Collections.reverse(personIdList);

        for (Long id : personIdListComplete) {
            if (!personIdList.contains(id)) {
                personIdList.add(id);
            }
        }
        return personIdList;
    }

    public Map<String, String> fetchAppCategory() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM ApplicationCategory i");

        List<ApplicationCategory> appsList = query.getResultList();
        Map<String, String> outDictionary = new HashMap<>();
        for (ApplicationCategory app : appsList) {
            outDictionary.put(app.getApplication(), app.getCategory());
        }
        em.close();
        return outDictionary;
    }

    public List<Person> fetchPersons() {
        EntityManager em = JPAUtil.getEntityManager();
//        Query query = em.createQuery("SELECT i FROM Person i WHERE i.id = 40 ORDER BY i.id");
        Query query = em.createQuery("SELECT i FROM Person i ORDER BY i.id");
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    public String fetchGender(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i.gender FROM Person i WHERE i.id = :id");
        query.setParameter("id", userId);
        try {
            return (String) query.getResultList().get(0);
        } catch (Exception e) {
            return "";
        } finally {
            em.close();
        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
