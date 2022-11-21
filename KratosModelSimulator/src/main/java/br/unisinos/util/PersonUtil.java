/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.util;

import br.unisinos.pojo.Person;
import br.unisinos.util.JPAUtil;
import java.util.ArrayList;
import java.util.List;
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

    private Person createNewSimplePerson(Long id) {

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
        Query query = em.createQuery("SELECT i.id FROM Person i");
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
        em.close();
        em.close();
        em.close();
        em.close();
    }

}
