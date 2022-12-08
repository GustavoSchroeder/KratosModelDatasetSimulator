/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.util;

import br.unisinos.pojo.ApplicationCategory;
import br.unisinos.pojo.Person;
import java.util.ArrayList;
import java.util.HashMap;
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
        Query query = em.createQuery("SELECT i FROM Person i WHERE i.id = 0 ORDER BY i.id");
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}
