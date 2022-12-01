/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model.generator;

import br.unisinos.pojo.ContextInformation.ApplicationUse;
import br.unisinos.pojo.Person;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.PersonUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class ApplicationUseGenerator {

    public Map<Long, Map<String, Map<Integer, Map<Integer, List<ApplicationUse>>>>> fetchApplications(List<Person> persons) {
        //User -> Day (1-n) -> Hour (0 - n) -> Application;Category
        Map<Long, Map<String, Map<Integer, Map<Integer, List<ApplicationUse>>>>> dictionaryApps = new HashMap<>();
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

        for (Person person : persons) {
            Map<String, Integer> dictionaryDate = new HashMap<>();
            Integer indexDate = 1;

            if (null == dictionaryApps.get(person.getId())) {
                dictionaryApps.put(person.getId(), new HashMap<>());
            }

            List<ApplicationUse> listApp = fetchApplicationUse(person.getId());
            for (ApplicationUse applicationUse : listApp) {
                String dayType = checkWeekDay(applicationUse.getOpenDate());

                if (null == dictionaryApps.get(person.getId()).get(dayType)) {
                    dictionaryApps.get(person.getId()).put(dayType, new HashMap<>());
                }

                String auxDate = sdfDate.format(applicationUse.getOpenDate());
                if (null == dictionaryDate.get(auxDate)) {
                    dictionaryDate.put(auxDate, indexDate++);
                }

                Integer iDate = dictionaryDate.get(auxDate);
                if (null == dictionaryApps.get(person.getId()).get(dayType).get(iDate)) {
                    dictionaryApps.get(person.getId()).get(dayType).put(iDate, new HashMap<>());
                }

                Integer hour = fetchHour(applicationUse.getOpenDate());
                if (null == dictionaryApps.get(person.getId()).get(dayType).get(iDate).get(hour)) {
                    dictionaryApps.get(person.getId()).get(dayType).get(iDate).put(hour, new ArrayList<>());
                }

                dictionaryApps.get(person.getId()).get(dayType).get(iDate).get(hour).add(applicationUse);
            }
        }
        return dictionaryApps;
    }

    private List<ApplicationUse> fetchApplicationUse(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM ApplicationUse i WHERE i.eventType IN (:events) AND i.person.id = :id");
        query.setParameter("id", id);
        query.setParameter("events", createListAllowedEvents());
        List<ApplicationUse> listApp = query.getResultList();
        em.close();
        return listApp;
    }

    private Integer fetchHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    private String checkWeekDay(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return "Weekend";
        }
        return "Weekday";
    }

    private List<String> createListAllowedEvents() {
        List<String> events = new ArrayList<>();
        events.add("User Interaction");
        events.add("Opened");
        return events;
    }

}
