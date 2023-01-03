/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.model.generator.ApplicationUseGenerator;
import br.unisinos.pojo.ContextInformation.ApplicationUse;
import br.unisinos.pojo.Person;
import br.unisinos.util.PersonUtil;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.TimeUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gustavolazarottoschroeder Import files from
 * https://github.com/aliannejadi/LSApp
 */
public class ImportApplicationUse implements Serializable {

    private final PersonUtil personUtil;
    private final TimeUtil timeUtil;
    private ApplicationUseGenerator applicationUseGenerator;

    public ImportApplicationUse() {
        this.personUtil = new PersonUtil();
        this.timeUtil = new TimeUtil();
        this.applicationUseGenerator = new ApplicationUseGenerator();
    }

    public void importFiles() throws FileNotFoundException, IOException, ParseException {
        String folder = "./src/main/java/files/smartphone_use/lsapp.tsv";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Map<Long, Integer> counter = new HashMap<>();
        Map<Long, List<ApplicationUse>> appsInUseMap = new HashMap<>();

        Map<Long, Person> personList = this.personUtil.findPersonList();
        Map<String, String> appsCategory = this.personUtil.fetchAppCategory();

        EntityManager em = JPAUtil.getEntityManager();
        try {
            try ( BufferedReader br = new BufferedReader(new FileReader(folder))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split("	");

                    if (values[1].equalsIgnoreCase("session_id")) {
                        continue;
                    }

                    Long idPerson = Long.parseLong(values[0]);
                    Long sessionId = Long.parseLong(values[1]);
                    String time = values[2].trim();
                    Date dateTime = sdf.parse(time);
                    String appName = values[3].trim();
                    String eventType = values[4].trim();
                    String appCategory = appsCategory.get(appName);

//                            System.out.println(idPerson + ";"
//                                    + sessionId + ";"
//                                    + time + ";"
//                                    + appName + ";"
//                                    + eventType
//                            );
                    if (counter.get(idPerson) == null) {
                        counter.put(idPerson, 0);
                    }
                    counter.put(idPerson, counter.get(idPerson) + 1);

                    if (appsInUseMap.get(idPerson) == null) {
                        appsInUseMap.put(idPerson, new ArrayList<>());
                    }

                    Person person = null;

                    ApplicationUse appInUse = new ApplicationUse(
                            person,
                            sessionId,
                            dateTime,
                            appName,
                            eventType,
                            appCategory,
                            dateTime,
                            this.timeUtil.checkWeekDay(dateTime)
                    );
                    appsInUseMap.get(idPerson).add(appInUse);
                }

            }
        } catch (Exception e) {
        }

        em.getTransaction().begin();

        //mergeInformation(em, appsInUseMap, personList);
        mergeInformation(em, appsInUseMap);

        deleteDataset();
        em.getTransaction().commit();
        em.close();
    }

    private void mergeInformation(EntityManager em, Map<Long, List<ApplicationUse>> appsInUseMap, Map<Long, Person> personList) {
        List<Integer> positionsGone = new ArrayList<>();

        List<Long> ids = new ArrayList<>(appsInUseMap.keySet());
        Integer max = ids.size() - 1;

        Integer mergeCounter = 0;
        Random rand = new Random();
        for (Integer i = 0; i < 60; i++) {
            System.out.println(i);
            Integer n = null;
            do {
                n = rand.nextInt(max);
            } while (positionsGone.contains(n)
                    || !checkMinDays(appsInUseMap.get(ids.get(n)))
                    || !checkTypesDay(appsInUseMap.get(ids.get(n))));

            positionsGone.add(n);
            List<ApplicationUse> apps = appsInUseMap.get(ids.get(n));

            Long idPerson = i.longValue();
            Person person = personList.get(idPerson);

            if (null == person) {
                System.out.println(idPerson);
                if (idPerson <= 60L) {
                    this.personUtil.createNewSimplePerson(idPerson);
                    personList = this.personUtil.findPersonList();
                    person = personList.get(idPerson);
                } else {
                    continue;
                }
            }

            for (ApplicationUse app : apps) {
                app.setPerson(person);
                em.merge(app);

                if (mergeCounter++ == 500) {
                    em.flush();
                    em.clear();
                    mergeCounter = 0;
                }
            }
        }

        //Inser the applications not linked with users in the database
        for (int i = 0; i < ids.size(); i++) {
            if (positionsGone.contains(i)) {
                List<ApplicationUse> apps = appsInUseMap.get(ids.get(i));
                for (ApplicationUse app : apps) {
                    app.setPositionLinked(i);
                    em.merge(app);

                    if (mergeCounter++ == 500) {
                        em.flush();
                        em.clear();
                        mergeCounter = 0;
                    }
                }
            }

        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private List<Long> createListIdsBySize(Map<Long, List<ApplicationUse>> dictionaryApps) {
        Map<Long, Integer> quantityMap = new HashMap<>();
        for (Map.Entry<Long, List<ApplicationUse>> entry : dictionaryApps.entrySet()) {
            Long key = entry.getKey();
            List<ApplicationUse> val = entry.getValue();
            Integer[] minutesLockedUnlocked = this.applicationUseGenerator.calculateApplications(new ArrayList<>(val));
            Integer minutesUnlocked = minutesLockedUnlocked[0];
            quantityMap.put(key, minutesUnlocked);
        }

        quantityMap = sortByValue(quantityMap);
        return (new ArrayList<>(quantityMap.keySet()));
    }

    private void mergeInformation(EntityManager em, Map<Long, List<ApplicationUse>> appsInUseMap) {
        List<Long> appsSize = createListIdsBySize(appsInUseMap);
        Collections.reverse(appsSize);
        List<Long> personList = this.personUtil.fetchUserIdsBasedStress(em);

        List<Integer> positionsGone = new ArrayList<>();

        Random rand = new Random();

        Integer mergeCounter = 0;
        //Inser the applications not linked with users in the database
        Integer counter = 0;
        for (int i = 0; i < personList.size(); i++) {
            Integer n;
            if (i <= 20) {
                do {
                    n = counter++;
                } while (!containsWeekAndWeekend(appsInUseMap.get(appsSize.get(n))));

            } else {
                do {
                    n = rand.nextInt(appsSize.size());
                } while (positionsGone.contains(n) || (appsInUseMap.get(appsSize.get(n)).size() < 500) || !containsWeekAndWeekend(appsInUseMap.get(appsSize.get(n))));
            }

            positionsGone.add(n);

            if (positionsGone.contains(n)) {
                List<ApplicationUse> apps = appsInUseMap.get(appsSize.get(n));
                Person p = em.find(Person.class, personList.get(i));
                System.out.println(p.getId() + ";" + p.getGender() + ";" + apps.size());
                for (ApplicationUse app : apps) {
                    app.setPositionLinked(i);
                    app.setPerson(p);
                    em.merge(app);

                    if (mergeCounter++ == 1500) {
                        em.flush();
                        em.clear();
                        mergeCounter = 0;
                    }
                }
            }
        }
    }

    private Boolean containsWeekAndWeekend(List<ApplicationUse> apps) {
        Boolean containsWeekDay = Boolean.FALSE;
        Boolean containsWeekend = Boolean.FALSE;

        for (ApplicationUse app : apps) {
            String dayType = this.timeUtil.checkWeekDay(app.getOpenDate());
            if (dayType.equalsIgnoreCase("Weekend")) {
                containsWeekend = Boolean.TRUE;
            } else {
                containsWeekDay = Boolean.TRUE;
            }
        }

        return containsWeekDay && containsWeekend;
    }

    private Boolean checkMinDays(List<ApplicationUse> applications) {
        Set<Integer> days = new HashSet<>();
        for (ApplicationUse application : applications) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(application.getOpenDate());
            days.add(cal.get(Calendar.DAY_OF_MONTH));
        }
        return days.size() > 10;
    }

    public Boolean checkTypesDay(List<ApplicationUse> applications) {
        Set<String> weekType = new HashSet<>();
        for (ApplicationUse application : applications) {

            weekType.add(this.timeUtil.checkWeekDay(application.getOpenDate()));
        }

        return weekType.size() == 2;
    }

    private void deleteDataset() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM ApplicationUse m");
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            em.close();
        }
    }
}
