/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model.generator;

import br.unisinos.pojo.ContextHistorySmartphoneUse;
import br.unisinos.pojo.PersonaSmartphoneAddiction;
import br.unisinos.util.JPAUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class GeneratePersonas {

    public void generatePersonas() {
        List<ContextHistorySmartphoneUse> listContext = fetchListContext();
        Set<Boolean> typeUserList = new HashSet<>();
        Set<String> genderList = new HashSet<>();
        Set<Integer> educationLevelList = new HashSet<>();
        Set<String> dayShift = new HashSet<>();
        Set<String> dayType = new HashSet<>();
        Set<String> ageCategory = new HashSet<>();

        Map<Boolean, Map<String, Map<String, Map<String, Map<String, List<ContextHistorySmartphoneUse>>>>>> map = new HashMap<>();

        for (ContextHistorySmartphoneUse context : listContext) {
            String ageCat = categorizeAge(context.getPerson().getAge());

            typeUserList.add(context.getSmartphoneAddicted());
            genderList.add(context.getPerson().getGender());
            educationLevelList.add(context.getPerson().getEducationalLevel());
            dayShift.add(context.getDayShift());
            dayType.add(context.getDayType());
            ageCategory.add(ageCat);

            if (null == map.get(context.getSmartphoneAddicted())) {
                map.put(context.getSmartphoneAddicted(), new HashMap<>());
            }

            if (null == map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())) {
                map.get(context.getSmartphoneAddicted())
                        .put(context.getPerson().getGender(), new HashMap<>());
            }

            if (null == map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())
                    .get(context.getDayShift())) {
                map.get(context.getSmartphoneAddicted())
                        .get(context.getPerson().getGender())
                        .put(context.getDayShift(), new HashMap<>());
            }

            if (null == map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())
                    .get(context.getDayShift())
                    .get(context.getDayType())) {
                map.get(context.getSmartphoneAddicted())
                        .get(context.getPerson().getGender())
                        .get(context.getDayShift())
                        .put(context.getDayType(), new HashMap<>());
            }

            if (null == map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())
                    .get(context.getDayShift())
                    .get(context.getDayType())
                    .get(ageCat)) {
                map.get(context.getSmartphoneAddicted())
                        .get(context.getPerson().getGender())
                        .get(context.getDayShift())
                        .get(context.getDayType())
                        .put(ageCat, new ArrayList<>());
            }

            map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())
                    .get(context.getDayShift())
                    .get(context.getDayType())
                    .get(ageCat).add(context);
        }

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        for (Boolean addicted : typeUserList) {
            for (String gender : genderList) {
                for (Integer education : educationLevelList) {
                    for (String shift : dayShift) {
                        for (String type : dayType) {
                            for (String ageCat : ageCategory) {
                                String id = addicted + ";" + gender + ";" + shift + ";" + type + ";" + ageCat;

                                PersonaSmartphoneAddiction p = new PersonaSmartphoneAddiction();
                                p.setId(id);
                                p.setTypeUser(addicted ? "Smartphone Addicted Behavior" : "Normal Behavior");
                                p.setGender(gender);
                                p.setAgeCategory(ageCat);
                                //p.setEducationLevel(education);
                                p.setDayShift(shift);
                                p.setDayType(type);

                                System.out.println(id);

                                List<ContextHistorySmartphoneUse> contextList = null;
                                try {
                                    contextList = map.get(addicted)
                                            .get(gender)
                                            .get(shift)
                                            .get(type)
                                            .get(ageCat);
                                } catch (Exception e) {
                                    System.out.println("error-stop");
                                }

                                if (null == contextList) {
                                    continue;
                                }

                                Integer appMostUsedTimeInUse = 0;
                                Integer applicationUseTime = 0;
                                Integer minutesUnlocked = 0;
                                Integer minutesLocked = 0;
                                Integer batteryLevel = 0;
                                //powerEvent
                                Integer quantityNotifications = 0;
                                Integer categoryNotificationsNumb = 0;
                                //mood
                                Integer sleepHoursEMA = 0;
                                Integer sleepRateEMA = 0;
                                Integer stressLevelEMA = 0;
                                Integer moodEMA = 0;
                                //DASS21
                                Integer stressScore = 0;
                                Integer anxietyScore = 0;
                                Integer depressionScore = 0;

                                for (ContextHistorySmartphoneUse chs : contextList) {
                                    appMostUsedTimeInUse += chs.getAppMostUsedTimeInUse();
                                    applicationUseTime += chs.getApplicationUseTime();
                                    minutesUnlocked += chs.getMinutesUnlocked();
                                    minutesLocked += chs.getMinutesLocked();
                                    batteryLevel += chs.getBatteryLevel().intValue();
                                    quantityNotifications += chs.getQuantityNotifications();
                                    categoryNotificationsNumb += chs.getCategoryNotificationsNumb();
                                    sleepHoursEMA += chs.getSleepHoursEMA();
                                    sleepRateEMA += chs.getSleepRateEMA();
                                    stressLevelEMA += chs.getStressLevelEMA();
                                    try {
                                        moodEMA += chs.getMoodEMA();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        stressScore += chs.getStressScore();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    anxietyScore += chs.getAnxietyScore();
                                    depressionScore += chs.getDepressionScore();
                                }

                                appMostUsedTimeInUse = (int) appMostUsedTimeInUse / contextList.size();
                                applicationUseTime = (int) applicationUseTime / contextList.size();
                                minutesUnlocked = (int) minutesUnlocked / contextList.size();
                                minutesLocked = (int) minutesLocked / contextList.size();
                                batteryLevel = (int) batteryLevel / contextList.size();
                                quantityNotifications = (int) quantityNotifications / contextList.size();
                                categoryNotificationsNumb = (int) categoryNotificationsNumb / contextList.size();
                                sleepHoursEMA = (int) sleepHoursEMA / contextList.size();
                                sleepRateEMA = (int) sleepRateEMA / contextList.size();
                                stressLevelEMA = (int) stressLevelEMA / contextList.size();
                                moodEMA = (int) moodEMA / contextList.size();
                                stressScore = (int) stressScore / contextList.size();
                                anxietyScore = (int) anxietyScore / contextList.size();
                                depressionScore = (int) depressionScore / contextList.size();

                                p.setAppMostUsedTimeInUse(appMostUsedTimeInUse);
                                p.setApplicationUseTime(applicationUseTime);
                                p.setMinutesUnlocked(minutesUnlocked);
                                p.setMinutesLocked(minutesLocked);
                                p.setBatteryLevel(batteryLevel);
                                p.setQuantityNotifications(quantityNotifications);
                                p.setCategoryNotificationsNumb(categoryNotificationsNumb);
                                p.setSleepHoursEMA(sleepHoursEMA);
                                p.setSleepRateEMA(sleepRateEMA);
                                p.setStressLevelEMA(stressLevelEMA);
                                p.setMoodEMA(moodEMA);
                                p.setStressScore(stressScore);
                                p.setAnxietyScore(anxietyScore);
                                p.setDepressionScore(depressionScore);
                                em.merge(p);
                            }
                        }
                    }
                }
            }
        }

        em.getTransaction().commit();
        em.close();
    }

    public String generatePersonas(List<ContextHistorySmartphoneUse> listContext) {
        Set<Boolean> typeUserList = new HashSet<>();
        Set<String> genderList = new HashSet<>();
        Set<Integer> educationLevelList = new HashSet<>();
        Set<String> dayShift = new HashSet<>();
        Set<String> dayType = new HashSet<>();
        Set<String> ageCategory = new HashSet<>();
        
        String output = "";

        Map<Boolean, Map<String, Map<String, Map<String, Map<String, List<ContextHistorySmartphoneUse>>>>>> map = new HashMap<>();

        for (ContextHistorySmartphoneUse context : listContext) {
            String ageCat = categorizeAge(context.getPerson().getAge());

            typeUserList.add(context.getSmartphoneAddicted());
            genderList.add(context.getPerson().getGender());
            educationLevelList.add(context.getPerson().getEducationalLevel());
            dayShift.add(context.getDayShift());
            dayType.add(context.getDayType());
            ageCategory.add(ageCat);

            if (null == map.get(context.getSmartphoneAddicted())) {
                map.put(context.getSmartphoneAddicted(), new HashMap<>());
            }

            if (null == map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())) {
                map.get(context.getSmartphoneAddicted())
                        .put(context.getPerson().getGender(), new HashMap<>());
            }

            if (null == map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())
                    .get(context.getDayShift())) {
                map.get(context.getSmartphoneAddicted())
                        .get(context.getPerson().getGender())
                        .put(context.getDayShift(), new HashMap<>());
            }

            if (null == map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())
                    .get(context.getDayShift())
                    .get(context.getDayType())) {
                map.get(context.getSmartphoneAddicted())
                        .get(context.getPerson().getGender())
                        .get(context.getDayShift())
                        .put(context.getDayType(), new HashMap<>());
            }

            if (null == map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())
                    .get(context.getDayShift())
                    .get(context.getDayType())
                    .get(ageCat)) {
                map.get(context.getSmartphoneAddicted())
                        .get(context.getPerson().getGender())
                        .get(context.getDayShift())
                        .get(context.getDayType())
                        .put(ageCat, new ArrayList<>());
            }

            map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())
                    .get(context.getDayShift())
                    .get(context.getDayType())
                    .get(ageCat).add(context);
        }

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        for (Boolean addicted : typeUserList) {
            for (String gender : genderList) {
                for (Integer education : educationLevelList) {
                    for (String shift : dayShift) {
                        for (String type : dayType) {
                            for (String ageCat : ageCategory) {
                                String id = addicted + ";" + gender + ";" + shift + ";" + type + ";" + ageCat;

                                PersonaSmartphoneAddiction p = new PersonaSmartphoneAddiction();
                                p.setId(id);
                                p.setTypeUser(addicted ? "Smartphone Addicted Behavior" : "Normal Behavior");
                                p.setGender(gender);
                                p.setAgeCategory(ageCat);
                                //p.setEducationLevel(education);
                                p.setDayShift(shift);
                                p.setDayType(type);

                                List<ContextHistorySmartphoneUse> contextList = null;
                                try {
                                    contextList = map.get(addicted)
                                            .get(gender)
                                            .get(shift)
                                            .get(type)
                                            .get(ageCat);
                                } catch (Exception e) {
                                    System.out.println("error-stop");
                                }

                                if (null == contextList) {
                                    continue;
                                }

                                Integer appMostUsedTimeInUse = 0;
                                Integer applicationUseTime = 0;
                                Integer minutesUnlocked = 0;
                                Integer minutesLocked = 0;
                                Integer batteryLevel = 0;
                                //powerEvent
                                Integer quantityNotifications = 0;
                                Integer categoryNotificationsNumb = 0;
                                //mood
                                Integer sleepHoursEMA = 0;
                                Integer sleepRateEMA = 0;
                                Integer stressLevelEMA = 0;
                                Integer moodEMA = 0;
                                //DASS21
                                Integer stressScore = 0;
                                Integer anxietyScore = 0;
                                Integer depressionScore = 0;

                                for (ContextHistorySmartphoneUse chs : contextList) {
                                    appMostUsedTimeInUse += chs.getAppMostUsedTimeInUse();
                                    applicationUseTime += chs.getApplicationUseTime();
                                    minutesUnlocked += chs.getMinutesUnlocked();
                                    minutesLocked += chs.getMinutesLocked();
                                    batteryLevel += chs.getBatteryLevel().intValue();
                                    quantityNotifications += chs.getQuantityNotifications();
                                    categoryNotificationsNumb += chs.getCategoryNotificationsNumb();
                                    sleepHoursEMA += chs.getSleepHoursEMA();
                                    sleepRateEMA += chs.getSleepRateEMA();
                                    stressLevelEMA += chs.getStressLevelEMA();
                                    try {
                                        moodEMA += chs.getMoodEMA();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        stressScore += chs.getStressScore();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    anxietyScore += chs.getAnxietyScore();
                                    depressionScore += chs.getDepressionScore();
                                }

                                appMostUsedTimeInUse = (int) appMostUsedTimeInUse / contextList.size();
                                applicationUseTime = (int) applicationUseTime / contextList.size();
                                minutesUnlocked = (int) minutesUnlocked / contextList.size();
                                minutesLocked = (int) minutesLocked / contextList.size();
                                batteryLevel = (int) batteryLevel / contextList.size();
                                quantityNotifications = (int) quantityNotifications / contextList.size();
                                categoryNotificationsNumb = (int) categoryNotificationsNumb / contextList.size();
                                sleepHoursEMA = (int) sleepHoursEMA / contextList.size();
                                sleepRateEMA = (int) sleepRateEMA / contextList.size();
                                stressLevelEMA = (int) stressLevelEMA / contextList.size();
                                moodEMA = (int) moodEMA / contextList.size();
                                stressScore = (int) stressScore / contextList.size();
                                anxietyScore = (int) anxietyScore / contextList.size();
                                depressionScore = (int) depressionScore / contextList.size();

                                p.setAppMostUsedTimeInUse(appMostUsedTimeInUse);
                                p.setApplicationUseTime(applicationUseTime);
                                p.setMinutesUnlocked(minutesUnlocked);
                                p.setMinutesLocked(minutesLocked);
                                p.setBatteryLevel(batteryLevel);
                                p.setQuantityNotifications(quantityNotifications);
                                p.setCategoryNotificationsNumb(categoryNotificationsNumb);
                                p.setSleepHoursEMA(sleepHoursEMA);
                                p.setSleepRateEMA(sleepRateEMA);
                                p.setStressLevelEMA(stressLevelEMA);
                                p.setMoodEMA(moodEMA);
                                p.setStressScore(stressScore);
                                p.setAnxietyScore(anxietyScore);
                                p.setDepressionScore(depressionScore);
                                em.merge(p);
                                output += (id
                                        + "," + appMostUsedTimeInUse
                                        + "," + applicationUseTime
                                        + "," + minutesUnlocked
                                        + "," + minutesLocked
                                        + "," + batteryLevel
                                        + "," + quantityNotifications
                                        + "," + categoryNotificationsNumb
                                        + "," + sleepHoursEMA
                                        + "," + sleepRateEMA
                                        + "," + stressLevelEMA
                                        + "," + moodEMA
                                        + "," + stressScore
                                        + "," + anxietyScore
                                        + "," + depressionScore
                                        + "\n"
                                );
                            }
                        }
                    }
                }
            }
        }

        deleteProfiles();
        em.getTransaction().commit();
        em.close();
        return output;
    }

    public String categorizeAge(Integer age) {
        if (age <= 1) {
            return "Infant";
        } else if (age >= 2 && age <= 4) {
            return "Toddler";
        } else if (age >= 5 && age <= 12) {
            return "Child";
        } else if (age >= 13 && age <= 19) {
            return "Teen";
        } else if (age >= 20 && age <= 39) {
            return "Adult";
        } else if (age >= 40 && age <= 59) {
            return "Middle Age Adult";
        } else if (age >= 60) {
            return "Senior Adult";
        }
        return "-";
    }

    private List<ContextHistorySmartphoneUse> fetchListContext() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM ContextHistorySmartphoneUse i");
        List<ContextHistorySmartphoneUse> list = query.getResultList();
        em.close();
        return list;
    }

    public Map<String, List<PersonaSmartphoneAddiction>> fetchProfiles() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM PersonaSmartphoneAddiction i");
        List<PersonaSmartphoneAddiction> personas = query.getResultList();
        Map<String, List<PersonaSmartphoneAddiction>> dictionary = new HashMap<>();
        for (PersonaSmartphoneAddiction persona : personas) {
            String key = persona.getGender()
                    + ";" + persona.getDayShift()
                    + ";" + persona.getDayType()
                    + ";" + persona.getAgeCategory();

            if (null == dictionary.get(key)) {
                dictionary.put(key, new ArrayList<>());
            }
            dictionary.get(key).add(persona);
        }

        em.close();
        return dictionary;
    }

    public void deleteProfiles() {
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
