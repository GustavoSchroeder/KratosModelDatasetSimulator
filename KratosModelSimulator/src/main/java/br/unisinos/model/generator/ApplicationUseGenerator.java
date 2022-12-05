/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model.generator;

import br.unisinos.pojo.ContextInformation.ApplicationUse;
import br.unisinos.pojo.Person;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.TimeUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class ApplicationUseGenerator {

    private TimeUtil timeUtil;

    public ApplicationUseGenerator() {
        this.timeUtil = new TimeUtil();
    }

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
                String dayType = this.timeUtil.checkWeekDay(applicationUse.getOpenDate());

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

                Integer hour = this.timeUtil.fetchHour(applicationUse.getOpenDate());
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
        Query query = em.createQuery("SELECT i FROM ApplicationUse i WHERE i.person.id = :id");
        query.setParameter("id", id);
        List<ApplicationUse> listApp = query.getResultList();
        em.close();
        return listApp;
    }

    private List<String> createListAllowedEvents() {
        List<String> events = new ArrayList<>();
        events.add("Opened");
        events.add("User Interaction");
        return events;
    }

    public Map<Integer, List<ApplicationUse>> randomDayApplicationDay(
            Map<Integer, Map<Integer, List<ApplicationUse>>> dictionary) {

        List<Integer> keySet = new ArrayList<>(dictionary.keySet());
        Random rand = new Random();

        Integer n;
        if (keySet.size() > 1) {
            n = rand.nextInt((keySet.size() - 1));
        } else {
            n = 0;
        }

        return dictionary.get(keySet.get(n));
    }

    private List<ApplicationUse> compareAppUse(List<ApplicationUse> appsInUse) {
        // Order List of Application by Time
        try {
            Collections.sort(appsInUse, new Comparator<ApplicationUse>() {
                public int compare(ApplicationUse o1, ApplicationUse o2) {
                    return o1.getOpenDate().compareTo(o2.getOpenDate());
                }
            });
        } catch (Exception e) {
        }

        return appsInUse;
    }

    public Map<String, Long> calculateCategoryTimeSpent(List<ApplicationUse> appsInUse) {
        appsInUse = compareAppUse(appsInUse);

        Map<String, Long> dictionaryCategoryMinutes = new HashMap<>();
        Long seconds = 0L;
        Date startTime = null;
        String category = null;

        for (ApplicationUse applicationUse : appsInUse) {

            //if application was oppened save time
            if (null == startTime) {
                if (applicationUse.getEventType().equalsIgnoreCase("Opened")) {
                    startTime = applicationUse.getOpenDate();
                    category = applicationUse.getAppName();
                }
            } else {

                //count how much time until close
                if (applicationUse.getEventType().equalsIgnoreCase("Closed")
                        && applicationUse.getAppName().equalsIgnoreCase(category)) {
                    seconds = (applicationUse.getOpenDate().getTime() - startTime.getTime()) / 1000;
                    //System.out.println(category + ";" + seconds);

                    category = applicationUse.getAppCategory();

                    if (null == dictionaryCategoryMinutes.get(category)) {
                        dictionaryCategoryMinutes.put(category, 0L);
                    }
                    dictionaryCategoryMinutes.put(category, dictionaryCategoryMinutes.get(category) + seconds);

                    seconds = 0L;
                    startTime = null;
                    category = null;
                }
            }
        }
        return dictionaryCategoryMinutes;
    }

    public Map<Integer, List<String>> analyseStringStatus(List<ApplicationUse> appsInUse) {
        // Todo
        // Analyze Screen Status based on applications open for hour
        // Dictionary contatins hour:time -> mm --> (Locked / Unlocked)

        appsInUse = compareAppUse(appsInUse);

        Map<Integer, List<String>> outputMap = new HashMap<>();
        for (int i = 0; i <= 60; i++) {
            if (null == outputMap.get(i)) {
                outputMap.put(i, new ArrayList<>());
            }

            if(null == appsInUse || appsInUse.isEmpty()){
                continue;
            }
            
            for (int j = 0; j < appsInUse.size(); j++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(appsInUse.get(j).getOpenDate());
                if (cal.get(Calendar.MINUTE) == i
                        && appsInUse.get(j).getEventType().equalsIgnoreCase("Opened")) {
                    outputMap.get(i).add("Unlocked;"
                            + appsInUse.get(j).getAppCategory()
                            + appsInUse.get(j).getAppName()
                    );
                    appsInUse.remove(j);
                    j--;
                }
            }
        }

        for (Map.Entry<Integer, List<String>> entry : outputMap.entrySet()) {
            Integer key = entry.getKey();
            List<String> val = entry.getValue();

            if (val.isEmpty()) {
                outputMap.get(key).add("Locked");
            }

        }

        return outputMap;
    }

    public Integer minutesLocked(Map<Integer, List<String>> dictionaryScreen) {
        Integer minutesLocked = 0;

        Loop1:
        for (Map.Entry<Integer, List<String>> entry : dictionaryScreen.entrySet()) {
            List<String> val = entry.getValue();

            for (String status : val) {
                if (status.equalsIgnoreCase("Locked")) {
                    minutesLocked++;
                    continue Loop1;
                }
            }
        }

        return minutesLocked;
    }

    public Integer minutesUnlocked(Map<Integer, List<String>> dictionaryScreen) {
        Integer minutesUnlocked = 0;

        Loop1:
        for (Map.Entry<Integer, List<String>> entry : dictionaryScreen.entrySet()) {
            List<String> val = entry.getValue();

            for (String status : val) {
                if (status.contains("Unlocked")) {
                    minutesUnlocked++;
                    continue Loop1;
                }
            }
        }

        return minutesUnlocked;
    }

    public TimeUtil getTimeUtil() {
        return timeUtil;
    }

    public void setTimeUtil(TimeUtil timeUtil) {
        this.timeUtil = timeUtil;
    }
}
