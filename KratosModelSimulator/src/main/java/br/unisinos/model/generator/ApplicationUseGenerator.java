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

    public Map<String, List<Long>> fetchApplicationsIds(String dayType, Long personId) {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i.openDate, i.id FROM ApplicationUse i WHERE i.dayType = :dayType and i.person.id = :id");
        query.setParameter("dayType", dayType);
        query.setParameter("id", personId);

        List<Object[]> auxObject = query.getResultList();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Map<String, List<Long>> dictionaryDateIds = new HashMap<>();
        for (Object[] obj : auxObject) {
            if (null == dictionaryDateIds.get(sdf.format((Date) obj[0]))) {
                dictionaryDateIds.put(sdf.format((Date) obj[0]), new ArrayList<>());
            }

            dictionaryDateIds.get(sdf.format((Date) obj[0])).add((Long) obj[1]);
        }
        em.close();
        return dictionaryDateIds;
    }

    private List<ApplicationUse> fetchApplicationsById(List<Long> ids) {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM ApplicationUse i WHERE i.id in (:ids)");
        query.setParameter("ids", ids);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }

    }

    public Map<Integer, List<ApplicationUse>> randomDayApplicationDay(Map<String, List<Long>> dictionaryDateIds) {

        List<String> keySet = new ArrayList<>(dictionaryDateIds.keySet());
        Random rand = new Random();

        // try ten times to find valid application information
        List<Integer> numbersExcluded = new ArrayList<>();
        Map<Integer, List<ApplicationUse>> dictionaryOut = new HashMap<>();
        Integer n = null;
        do {
            n = rand.nextInt((keySet.size()));
        } while (numbersExcluded.contains(n) && dictionaryDateIds.get(keySet.get(n)).size() < 5);

        List<Long> idsApps = dictionaryDateIds.get(keySet.get(n));
        List<ApplicationUse> apps = fetchApplicationsById(idsApps);

        for (ApplicationUse app : apps) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(app.getOpenDate());
            if (null == dictionaryOut.get(cal.get(Calendar.HOUR_OF_DAY))) {
                dictionaryOut.put(cal.get(Calendar.HOUR_OF_DAY), new ArrayList<>());
            }
            dictionaryOut.get(cal.get(Calendar.HOUR_OF_DAY)).add(app);
        }

        return dictionaryOut;
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
        String lastEventType = "";

        for (int i = 0; i < appsInUse.size(); i++) {
            ApplicationUse applicationUse = appsInUse.get(i);

            //if application was oppened save time
            if (((lastEventType.equalsIgnoreCase("User Interaction")
                    && !applicationUse.getEventType().equalsIgnoreCase("User Interaction")
                    && null != startTime))
                    || (lastEventType.equalsIgnoreCase("User Interaction") && ((i + 1) == appsInUse.size()) && appsInUse.size() > 1)) {

                seconds = (applicationUse.getOpenDate().getTime() - startTime.getTime()) / 1000;
                category = applicationUse.getAppCategory();

                if (null == dictionaryCategoryMinutes.get(category)) {
                    dictionaryCategoryMinutes.put(category, 0L);
                }
                dictionaryCategoryMinutes.put(category, dictionaryCategoryMinutes.get(category) + seconds);

                seconds = 0L;
                startTime = null;
                category = null;
            }

            lastEventType = applicationUse.getEventType();

            if (null == startTime) {

                if (applicationUse.getEventType().equalsIgnoreCase("User Interaction")) {
                    startTime = applicationUse.getOpenDate();
                    category = applicationUse.getAppName();
                    continue;
                }

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

    public Map<String, Long> calculateApplicationTimeSpent(List<ApplicationUse> appsInUse) {
        appsInUse = compareAppUse(appsInUse);

        Map<String, Long> dictionaryCategoryMinutes = new HashMap<>();
        Long seconds = 0L;
        Date startTime = null;
        String application = null;
        String lastEventType = "";

        for (int i = 0; i < appsInUse.size(); i++) {
            ApplicationUse applicationUse = appsInUse.get(i);

            //if application was oppened save time
            if (((lastEventType.equalsIgnoreCase("User Interaction")
                    && !applicationUse.getEventType().equalsIgnoreCase("User Interaction")
                    && null != startTime))
                    || (lastEventType.equalsIgnoreCase("User Interaction") && ((i + 1) == appsInUse.size()) && appsInUse.size() > 1)) {

                seconds = (applicationUse.getOpenDate().getTime() - startTime.getTime()) / 1000;
                application = applicationUse.getAppName();

                if (null == dictionaryCategoryMinutes.get(application)) {
                    dictionaryCategoryMinutes.put(application, 0L);
                }
                dictionaryCategoryMinutes.put(application, dictionaryCategoryMinutes.get(application) + seconds);

                seconds = 0L;
                startTime = null;
                application = null;
            }

            lastEventType = applicationUse.getEventType();

            if (null == startTime) {

                if (applicationUse.getEventType().equalsIgnoreCase("User Interaction")) {
                    startTime = applicationUse.getOpenDate();
                    application = applicationUse.getAppName();
                    continue;
                }

                if (applicationUse.getEventType().equalsIgnoreCase("Opened")) {
                    startTime = applicationUse.getOpenDate();
                    application = applicationUse.getAppName();
                }
            } else {

                //count how much time until close
                if (applicationUse.getEventType().equalsIgnoreCase("Closed")
                        && applicationUse.getAppName().equalsIgnoreCase(application)) {
                    seconds = (applicationUse.getOpenDate().getTime() - startTime.getTime()) / 1000;
                    //System.out.println(category + ";" + seconds);

                    application = applicationUse.getAppName();

                    if (null == dictionaryCategoryMinutes.get(application)) {
                        dictionaryCategoryMinutes.put(application, 0L);
                    }
                    dictionaryCategoryMinutes.put(application, dictionaryCategoryMinutes.get(application) + seconds);

                    seconds = 0L;
                    startTime = null;
                    application = null;
                }
            }
        }

        return dictionaryCategoryMinutes;
    }

    public Integer[] calculateApplications(List<ApplicationUse> appsInUse) {
        appsInUse = compareAppUse(appsInUse);

        Long seconds = 0L;
        Date startTime = null;
        String application = null;
        String lastEventType = "";
        Long useTime = 0L;

        for (int i = 0; i < appsInUse.size(); i++) {
            ApplicationUse applicationUse = appsInUse.get(i);

            //if application was oppened save time
            if (((lastEventType.equalsIgnoreCase("User Interaction")
                    && !applicationUse.getEventType().equalsIgnoreCase("User Interaction")
                    && null != startTime))
                    || (lastEventType.equalsIgnoreCase("User Interaction") && ((i + 1) == appsInUse.size()) && appsInUse.size() > 1)) {

                seconds = (applicationUse.getOpenDate().getTime() - startTime.getTime()) / 1000;

                useTime += seconds;

                seconds = 0L;
                startTime = null;
                application = null;
            }

            lastEventType = applicationUse.getEventType();

            if (null == startTime) {

                if (applicationUse.getEventType().equalsIgnoreCase("User Interaction")) {
                    startTime = applicationUse.getOpenDate();
                    application = applicationUse.getAppName();
                    continue;
                }

                if (applicationUse.getEventType().equalsIgnoreCase("Opened")) {
                    startTime = applicationUse.getOpenDate();
                    application = applicationUse.getAppName();
                }
            } else {

                //count how much time until close
                if (applicationUse.getEventType().equalsIgnoreCase("Closed")
                        && applicationUse.getAppName().equalsIgnoreCase(application)) {
                    seconds = (applicationUse.getOpenDate().getTime() - startTime.getTime()) / 1000;
                    //System.out.println(category + ";" + seconds);

                    useTime += seconds;

                    seconds = 0L;
                    startTime = null;
                    application = null;
                }
            }
        }

        Integer minutesUsing = (int) (useTime / 60);
        Integer minutesNotUsing = 60 - minutesUsing;
        Integer[] minutes = {minutesUsing, minutesNotUsing};
        return minutes;
    }

    public Object[] calculateTopTimeSpent(Map<String, Long> dictionaryCategoryMinutes) {
        Object[] output = new Object[2];
        Long maxMinutes = 0L;
        for (Map.Entry<String, Long> entry : dictionaryCategoryMinutes.entrySet()) {
            String key = entry.getKey();
            Long val = entry.getValue();

            if (val > maxMinutes) {
                output[0] = key;
                output[1] = val;
                maxMinutes = val;
            }
        }

        return output;
    }

    public Map<Integer, List<String>> analyseScreenStatus(List<ApplicationUse> appsInUse) {
        // Analyze Screen Status based on applications open for hour
        // Dictionary contatins hour:time -> mm --> (Locked / Unlocked)=
        appsInUse = compareAppUse(appsInUse);

        Map<Integer, List<String>> outputMap = new HashMap<>();
        for (int i = 0; i <= 60; i++) {
            if (null == outputMap.get(i)) {
                outputMap.put(i, new ArrayList<>());
            }

            if (null == appsInUse || appsInUse.isEmpty()) {
                continue;
            }

            for (int j = 0; j < appsInUse.size(); j++) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(appsInUse.get(j).getOpenDate());
                if (cal.get(Calendar.MINUTE) == i
                        && (appsInUse.get(j).getEventType().equalsIgnoreCase("Opened")
                        || appsInUse.get(j).getEventType().equalsIgnoreCase("User Interaction"))) {
                    outputMap.get(i).add("Unlocked;"
                            + appsInUse.get(j).getAppCategory()
                            + appsInUse.get(j).getAppName()
                    );
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

    public Object[] calculateScreeStatus(List<ApplicationUse> applications) {
        String applicationCategoryTopInUse = "";
        Long categoryUseTime = 0L;
        String appHighUseTime = "";
        Long applicationUseTime = 0L;

        Map<String, Long> categoryMinutes;
        Map<String, Long> applicationMinutes;
        // category time spent
        categoryMinutes = calculateCategoryTimeSpent(new ArrayList<>(applications));
        Object[] categoryTopSpent = calculateTopTimeSpent(categoryMinutes);
        applicationCategoryTopInUse = (String) categoryTopSpent[0];
        try {
            categoryUseTime = (((Long) categoryTopSpent[1]) / 60);
        } catch (Exception e) {
        }

        // application time spent
        applicationMinutes = calculateApplicationTimeSpent(new ArrayList<>(applications));
        Object[] applicationTopSpent = calculateTopTimeSpent(applicationMinutes);
        appHighUseTime = (String) applicationTopSpent[0];
        try {
            applicationUseTime = (((Long) applicationTopSpent[1]) / 60);
        } catch (Exception e) {
        }

        if (null == applicationCategoryTopInUse) {
            applicationCategoryTopInUse = "";
        }

        if (null == appHighUseTime) {
            appHighUseTime = "";
        }

        Object[] output = {applicationCategoryTopInUse, categoryUseTime, appHighUseTime, applicationUseTime};
        return output;
    }

    public TimeUtil getTimeUtil() {
        return timeUtil;
    }

    public void setTimeUtil(TimeUtil timeUtil) {
        this.timeUtil = timeUtil;
    }
}
