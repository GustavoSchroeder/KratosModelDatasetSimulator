/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model.generator;

import br.unisinos.pojo.ContextInformation.PhoneCharge;
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
public class DeviceInformationGenerator {

    public void organizePowerEvents(List<Person> persons) {
        for (Person person : persons) {
            List<PhoneCharge> phoneChargeList = fetchPowerEvent(person.getId());
        }
    }

    public List<PhoneCharge> fetchPowerEvent(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM PhoneCharge i WHERE i.person.id = :id");
        query.setParameter("id", id);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

}
