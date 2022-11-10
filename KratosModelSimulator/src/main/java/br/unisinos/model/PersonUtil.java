/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.pojo.Person;
import br.unisinos.util.JPAUtil;
import javax.persistence.EntityManager;

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

    private Person createNewSimplePerson(Long id) {

        Person p = new Person(id, "", "");
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        em.close();

        return p;
    }

}
