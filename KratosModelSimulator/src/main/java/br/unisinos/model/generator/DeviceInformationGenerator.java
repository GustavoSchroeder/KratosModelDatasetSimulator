/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model.generator;

import br.unisinos.pojo.ContextInformation.ApplicationUse;
import br.unisinos.pojo.ContextInformation.PhoneCharge;
import br.unisinos.pojo.Person;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.TimeUtil;
import java.text.SimpleDateFormat;
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
public class DeviceInformationGenerator {
    
    private TimeUtil timeUtil;

    public DeviceInformationGenerator() {
        this.timeUtil = new TimeUtil();
    }

    //TODO
    public Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>>> organizePowerEvents(List<Person> persons) {
        Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>>> dictionaryPhoneCharge = new HashMap<>();

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

        for (Person person : persons) {
            List<PhoneCharge> phoneChargeList = fetchPowerEvent(person.getId());

            Map<String, Integer> dictionaryDate = new HashMap<>();
            Integer indexDate = 1;

            if (null == dictionaryPhoneCharge.get(person.getId())) {
                dictionaryPhoneCharge.put(person.getId(), new HashMap<>());
            }

            for (PhoneCharge phoneCharge : phoneChargeList) {

                String dayType = this.timeUtil.checkWeekDay(phoneCharge.getStartTime());

                if (null == dictionaryPhoneCharge.get(person.getId()).get(dayType)) {
                    dictionaryPhoneCharge.get(person.getId()).put(dayType, new HashMap<>());
                }

                String auxDate = sdfDate.format(phoneCharge.getStartTime());
                if (null == dictionaryDate.get(auxDate)) {
                    dictionaryDate.put(auxDate, indexDate++);
                }

                Integer iDate = dictionaryDate.get(auxDate);
                if (null == dictionaryPhoneCharge.get(person.getId()).get(dayType).get(iDate)) {
                    dictionaryPhoneCharge.get(person.getId()).get(dayType).put(iDate, new HashMap<>());
                }

                Integer hour = this.timeUtil.fetchHour(phoneCharge.getStartTime());
                if (null == dictionaryPhoneCharge.get(person.getId()).get(dayType).get(iDate).get(hour)) {
                    dictionaryPhoneCharge.get(person.getId()).get(dayType).get(iDate).put(hour, new ArrayList<>());
                }
            }
        }
        return dictionaryPhoneCharge;
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

    public TimeUtil getTimeUtil() {
        return timeUtil;
    }

    public void setTimeUtil(TimeUtil timeUtil) {
        this.timeUtil = timeUtil;
    }
}
