/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model.generator;

import br.unisinos.pojo.ContextHistorySmartphoneUse;
import br.unisinos.pojo.PersonaSmartphoneAddiction;
import br.unisinos.util.JPAUtil;
import java.util.ArrayList;
import java.util.Collections;
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

        Map<Boolean, Map<String, Map<String, Map<String, List<ContextHistorySmartphoneUse>>>>> map = new HashMap<>();

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
                    .get(context.getDayType())) {
                map.get(context.getSmartphoneAddicted())
                        .get(context.getPerson().getGender())
                        .put(context.getDayType(), new HashMap<>());
            }

            if (null == map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())
                    .get(context.getDayType())
                    .get(ageCat)) {
                map.get(context.getSmartphoneAddicted())
                        .get(context.getPerson().getGender())
                        .get(context.getDayType())
                        .put(ageCat, new ArrayList<>());
            }

            map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())
                    .get(context.getDayType())
                    .get(ageCat).add(context);
        }

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        Map<String, List<Double>> values = instantiateMap();

        for (Boolean addicted : typeUserList) {
            for (String gender : genderList) {
                for (String type : dayType) {
                    for (String ageCat : ageCategory) {
                        String id = addicted + ";" + gender + ";" + type + ";" + ageCat;

                        PersonaSmartphoneAddiction p = new PersonaSmartphoneAddiction();
                        p.setId(id);
                        p.setTypeUser(addicted ? "Smartphone Addicted Behavior" : "Normal Behavior");
                        p.setGender(gender);
                        p.setAgeCategory(ageCat);
                        //p.setEducationLevel(education);
                        //p.setDayShift(shift);
                        p.setDayType(type);

                        System.out.println(id);

                        List<ContextHistorySmartphoneUse> contextList = null;
                        try {
                            contextList = map.get(addicted)
                                    .get(gender)
                                    .get(type)
                                    .get(ageCat);
                        } catch (Exception e) {
                            System.out.println("error-stop");
                        }

                        if (null == contextList) {
                            continue;
                        }

                        Double appMostUsedTimeInUse = 0.0;
                        Double applicationUseTime = 0.0;
                        Double minutesUnlocked = 0.0;
                        Double minutesLocked = 0.0;
                        Double batteryLevel = 0.0;
                        //powerEvent
                        Double quantityNotifications = 0.0;
                        Double categoryNotificationsNumb = 0.0;
                        //mood
                        Double sleepHoursEMA = 0.0;
                        Double sleepRateEMA = 0.0;
                        Double stressLevelEMA = 0.0;
                        Double moodEMA = 0.0;
                        //DASS21
                        Double stressScore = 0.0;
                        Double anxietyScore = 0.0;
                        Double depressionScore = 0.0;

                        for (ContextHistorySmartphoneUse chs : contextList) {
                            appMostUsedTimeInUse += chs.getAppMostUsedTimeInUse();
                            values.get("appMostUsedTimeInUse").add(chs.getAppMostUsedTimeInUse().doubleValue());
                            applicationUseTime += chs.getApplicationUseTime();
                            values.get("applicationUseTime").add(chs.getApplicationUseTime().doubleValue());
                            minutesUnlocked += chs.getMinutesUnlocked();
                            values.get("minutesUnlocked").add(chs.getMinutesUnlocked().doubleValue());
                            minutesLocked += chs.getMinutesLocked();
                            values.get("minutesLocked").add(chs.getMinutesLocked().doubleValue());
                            batteryLevel += chs.getBatteryLevel().intValue();
                            values.get("batteryLevel").add(chs.getBatteryLevel());
                            quantityNotifications += chs.getQuantityNotifications();
                            values.get("quantityNotifications").add(chs.getQuantityNotifications().doubleValue());
                            categoryNotificationsNumb += chs.getCategoryNotificationsNumb();
                            values.get("categoryNotificationsNumb").add(chs.getCategoryNotificationsNumb().doubleValue());
                            sleepHoursEMA += chs.getSleepHoursEMA();
                            values.get("sleepHoursEMA").add(chs.getSleepHoursEMA().doubleValue());
                            sleepRateEMA += chs.getSleepRateEMA();
                            values.get("sleepRateEMA").add(chs.getSleepRateEMA().doubleValue());
                            stressLevelEMA += chs.getStressLevelEMA();
                            values.get("stressLevelEMA").add(chs.getStressLevelEMA().doubleValue());
                            try {
                                moodEMA += chs.getMoodEMA();
                                values.get("moodEMA").add(chs.getMoodEMA().doubleValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                stressScore += chs.getStressScore();
                                values.get("stressScore").add(chs.getStressScore().doubleValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            anxietyScore += chs.getAnxietyScore();
                            values.get("anxietyScore").add(chs.getAnxietyScore().doubleValue());
                            depressionScore += chs.getDepressionScore();
                            values.get("depressionScore").add(chs.getDepressionScore().doubleValue());
                        }

                        appMostUsedTimeInUse = normalize(appMostUsedTimeInUse, fetchMinValue(values, "appMostUsedTimeInUse"), fetchMaxValue(values, "appMostUsedTimeInUse"));
                        applicationUseTime = normalize(applicationUseTime, fetchMinValue(values, "applicationUseTime"), fetchMaxValue(values, "applicationUseTime"));
                        minutesUnlocked = normalize(minutesUnlocked, fetchMinValue(values, "minutesUnlocked"), fetchMaxValue(values, "minutesUnlocked"));
                        minutesLocked = normalize(minutesLocked, fetchMinValue(values, "minutesLocked"), fetchMaxValue(values, "minutesLocked"));
                        batteryLevel = normalize(batteryLevel, fetchMinValue(values, "batteryLevel"), fetchMaxValue(values, "batteryLevel"));
                        quantityNotifications = normalize(quantityNotifications, fetchMinValue(values, "quantityNotifications"), fetchMaxValue(values, "quantityNotifications"));
                        categoryNotificationsNumb = normalize(categoryNotificationsNumb, fetchMinValue(values, "categoryNotificationsNumb"), fetchMaxValue(values, "categoryNotificationsNumb"));
                        sleepHoursEMA = normalize(sleepHoursEMA, fetchMinValue(values, "sleepHoursEMA"), fetchMaxValue(values, "sleepHoursEMA"));
                        sleepRateEMA = normalize(sleepRateEMA, fetchMinValue(values, "sleepRateEMA"), fetchMaxValue(values, "sleepRateEMA"));
                        stressLevelEMA = normalize(stressLevelEMA, fetchMinValue(values, "stressLevelEMA"), fetchMaxValue(values, "stressLevelEMA"));
                        moodEMA = normalize(moodEMA, fetchMinValue(values, "moodEMA"), fetchMaxValue(values, "moodEMA"));
                        stressScore = normalize(stressScore, fetchMinValue(values, "stressScore"), fetchMaxValue(values, "stressScore"));
                        anxietyScore = normalize(anxietyScore, fetchMinValue(values, "anxietyScore"), fetchMaxValue(values, "anxietyScore"));
                        depressionScore = normalize(depressionScore, fetchMinValue(values, "depressionScore"), fetchMaxValue(values, "depressionScore"));

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

        Map<Boolean, Map<String, Map<String, Map<String, List<ContextHistorySmartphoneUse>>>>> map = new HashMap<>();

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
                    .get(context.getDayType())) {
                map.get(context.getSmartphoneAddicted())
                        .get(context.getPerson().getGender())
                        .put(context.getDayType(), new HashMap<>());
            }

            if (null == map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())
                    .get(context.getDayType())
                    .get(ageCat)) {
                map.get(context.getSmartphoneAddicted())
                        .get(context.getPerson().getGender())
                        .get(context.getDayType())
                        .put(ageCat, new ArrayList<>());
            }

            map.get(context.getSmartphoneAddicted())
                    .get(context.getPerson().getGender())
                    .get(context.getDayType())
                    .get(ageCat).add(context);
        }

        deleteProfiles();
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        Map<String, List<Double>> values = instantiateMap();

        for (Boolean addicted : typeUserList) {
            for (String gender : genderList) {
                for (String type : dayType) {
                    for (String ageCat : ageCategory) {
                        //String id = addicted + ";" + gender + ";" + shift + ";" + type + ";" + ageCat;
                        String id = addicted + ";" + gender + ";" + type + ";" + ageCat;

                        PersonaSmartphoneAddiction p = new PersonaSmartphoneAddiction();
                        p.setId(id);
                        p.setTypeUser(addicted ? "Smartphone Addicted Behavior" : "Normal Behavior");
                        p.setGender(gender);
                        p.setAgeCategory(ageCat);
                        //p.setEducationLevel(education);
                        //p.setDayShift(shift);
                        p.setDayType(type);

                        List<ContextHistorySmartphoneUse> contextList = null;
                        try {
                            contextList = map.get(addicted)
                                    .get(gender)
                                    //        .get(shift)
                                    .get(type)
                                    .get(ageCat);
                        } catch (Exception e) {
                            System.out.println("error-stop");
                        }

                        if (null == contextList) {
                            continue;
                        }

                        Double appMostUsedTimeInUse = 0.0;
                        Double applicationUseTime = 0.0;
                        Double minutesUnlocked = 0.0;
                        Double minutesLocked = 0.0;
                        Double batteryLevel = 0.0;
                        //powerEvent
                        Double quantityNotifications = 0.0;
                        Double categoryNotificationsNumb = 0.0;
                        //mood
                        Double sleepHoursEMA = 0.0;
                        Double sleepRateEMA = 0.0;
                        Double stressLevelEMA = 0.0;
                        Double moodEMA = 0.0;
                        //DASS21
                        Double stressScore = 0.0;
                        Double anxietyScore = 0.0;
                        Double depressionScore = 0.0;
                        
                        Double nomophobia = 0.0;

                        for (ContextHistorySmartphoneUse chs : contextList) {
                            appMostUsedTimeInUse += chs.getAppMostUsedTimeInUse();
                            values.get("appMostUsedTimeInUse").add(chs.getAppMostUsedTimeInUse().doubleValue());
                            applicationUseTime += chs.getApplicationUseTime();
                            values.get("applicationUseTime").add(chs.getApplicationUseTime().doubleValue());
                            minutesUnlocked += chs.getMinutesUnlocked();
                            values.get("minutesUnlocked").add(chs.getMinutesUnlocked().doubleValue());
                            minutesLocked += chs.getMinutesLocked();
                            values.get("minutesLocked").add(chs.getMinutesLocked().doubleValue());
                            batteryLevel += chs.getBatteryLevel().intValue();
                            values.get("batteryLevel").add(chs.getBatteryLevel());
                            quantityNotifications += chs.getQuantityNotifications();
                            values.get("quantityNotifications").add(chs.getQuantityNotifications().doubleValue());
                            categoryNotificationsNumb += chs.getCategoryNotificationsNumb();
                            values.get("categoryNotificationsNumb").add(chs.getCategoryNotificationsNumb().doubleValue());
                            sleepHoursEMA += chs.getSleepHoursEMA();
                            values.get("sleepHoursEMA").add(chs.getSleepHoursEMA().doubleValue());
                            sleepRateEMA += chs.getSleepRateEMA();
                            values.get("sleepRateEMA").add(chs.getSleepRateEMA().doubleValue());
                            stressLevelEMA += chs.getStressLevelEMA();
                            values.get("stressLevelEMA").add(chs.getStressLevelEMA().doubleValue());
                            try {
                                moodEMA += chs.getMoodEMA();
                                values.get("moodEMA").add(chs.getMoodEMA().doubleValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                stressScore += chs.getStressScore();
                                values.get("stressScore").add(chs.getStressScore().doubleValue());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            anxietyScore += chs.getAnxietyScore();
                            values.get("anxietyScore").add(chs.getAnxietyScore().doubleValue());
                            depressionScore += chs.getDepressionScore();
                            values.get("depressionScore").add(chs.getDepressionScore().doubleValue());
                            
                            
                            nomophobia += chs.getTotalResultNomophobia();
                            values.get("nomophobia").add(chs.getTotalResultNomophobia().doubleValue());
                        }

                        appMostUsedTimeInUse = appMostUsedTimeInUse / contextList.size();
                        applicationUseTime = applicationUseTime / contextList.size();
                        minutesUnlocked = minutesUnlocked / contextList.size();
                        minutesLocked = minutesLocked / contextList.size();
                        batteryLevel = batteryLevel / contextList.size();
                        quantityNotifications = quantityNotifications / contextList.size();
                        categoryNotificationsNumb = categoryNotificationsNumb / contextList.size();
                        sleepHoursEMA = sleepHoursEMA / contextList.size();
                        sleepRateEMA = sleepRateEMA / contextList.size();
                        stressLevelEMA = stressLevelEMA / contextList.size();
                        moodEMA = moodEMA / contextList.size();
                        stressScore = stressScore / contextList.size();
                        anxietyScore = anxietyScore / contextList.size();
                        depressionScore = depressionScore / contextList.size();
                        nomophobia = nomophobia / contextList.size();

                        appMostUsedTimeInUse = normalize(appMostUsedTimeInUse, fetchMinValue(values, "appMostUsedTimeInUse"), fetchMaxValue(values, "appMostUsedTimeInUse"));
                        applicationUseTime = normalize(applicationUseTime, fetchMinValue(values, "applicationUseTime"), fetchMaxValue(values, "applicationUseTime"));
                        minutesUnlocked = normalize(minutesUnlocked, fetchMinValue(values, "minutesUnlocked"), fetchMaxValue(values, "minutesUnlocked"));
                        minutesLocked = normalize(minutesLocked, fetchMinValue(values, "minutesLocked"), fetchMaxValue(values, "minutesLocked"));
                        batteryLevel = normalize(batteryLevel, fetchMinValue(values, "batteryLevel"), fetchMaxValue(values, "batteryLevel"));
                        quantityNotifications = normalize(quantityNotifications, fetchMinValue(values, "quantityNotifications"), fetchMaxValue(values, "quantityNotifications"));
                        categoryNotificationsNumb = normalize(categoryNotificationsNumb, fetchMinValue(values, "categoryNotificationsNumb"), fetchMaxValue(values, "categoryNotificationsNumb"));
                        sleepHoursEMA = normalize(sleepHoursEMA, fetchMinValue(values, "sleepHoursEMA"), fetchMaxValue(values, "sleepHoursEMA"));
                        sleepRateEMA = normalize(sleepRateEMA, fetchMinValue(values, "sleepRateEMA"), fetchMaxValue(values, "sleepRateEMA"));
                        stressLevelEMA = normalize(stressLevelEMA, fetchMinValue(values, "stressLevelEMA"), fetchMaxValue(values, "stressLevelEMA"));
                        moodEMA = normalize(moodEMA, fetchMinValue(values, "moodEMA"), fetchMaxValue(values, "moodEMA"));
                        stressScore = normalize(stressScore, fetchMinValue(values, "stressScore"), fetchMaxValue(values, "stressScore"));
                        anxietyScore = normalize(anxietyScore, fetchMinValue(values, "anxietyScore"), fetchMaxValue(values, "anxietyScore"));
                        depressionScore = normalize(depressionScore, fetchMinValue(values, "depressionScore"), fetchMaxValue(values, "depressionScore"));
                        nomophobia = normalize(nomophobia, fetchMinValue(values, "nomophobia"), fetchMaxValue(values, "nomophobia"));

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
                        p.setNomophobiaRate(nomophobia);
                        
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
                                + "\n");
                    }
                }
            }
        }

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
                    //+ ";" + persona.getDayShift()
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
            Query query = em.createQuery("DELETE FROM PersonaSmartphoneAddiction m");
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
        } finally {
            em.close();
        }
    }

    public List<PersonaSmartphoneAddiction> fetchPersonas(String gender, String ageCategory) {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM PersonaSmartphoneAddiction i WHERE i.gender = :gender and i.ageCategory = :ageCategory");
        query.setParameter("gender", gender);
        query.setParameter("ageCategory", ageCategory);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    private Map<String, List<Double>> instantiateMap() {
        Map<String, List<Double>> values = new HashMap<>();

        values.put("appMostUsedTimeInUse", new ArrayList<>());
        values.put("applicationUseTime", new ArrayList<>());
        values.put("minutesUnlocked", new ArrayList<>());
        values.put("minutesLocked", new ArrayList<>());
        values.put("batteryLevel", new ArrayList<>());
        values.put("quantityNotifications", new ArrayList<>());
        values.put("categoryNotificationsNumb", new ArrayList<>());
        values.put("sleepHoursEMA", new ArrayList<>());
        values.put("sleepRateEMA", new ArrayList<>());
        values.put("stressLevelEMA", new ArrayList<>());
        values.put("moodEMA", new ArrayList<>());
        values.put("stressScore", new ArrayList<>());
        values.put("anxietyScore", new ArrayList<>());
        values.put("depressionScore", new ArrayList<>());
        values.put("nomophobia", new ArrayList<>());

        return values;
    }

    private Double fetchMaxValue(Map<String, List<Double>> mapValues, String key) {
        List<Double> values = mapValues.get(key);
        Collections.sort(values);
        Collections.reverse(values);
        return values.get(0);
    }

    private Double fetchMinValue(Map<String, List<Double>> mapValues, String key) {
        List<Double> values = mapValues.get(key);
        Collections.sort(values);
        return values.get(0);
    }

    private Double normalize(double value, double min, double max) {
        Double output = ((value - min) / (max - min));

        if (output.isNaN()) {
            System.out.println("stop");
            output = 0.0;
        }

        return output;
    }
}
