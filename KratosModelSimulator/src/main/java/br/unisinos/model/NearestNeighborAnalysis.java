package br.unisinos.model;

import br.unisinos.model.generator.ContextGenerator;
import br.unisinos.model.generator.GeneratePersonas;
import br.unisinos.pojo.ContextHistorySmartphoneUse;
import br.unisinos.pojo.PersonaSmartphoneAddiction;
import br.unisinos.util.JPAUtil;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class NearestNeighborAnalysis {

    private GeneratePersonas personasGenerator;
    private ContextGenerator contextGenerator;

    public NearestNeighborAnalysis() {
        this.personasGenerator = new GeneratePersonas();
        this.contextGenerator = new ContextGenerator();
    }

    private Double normalize(double value, double min, double max) {
        return ((value - min) / (max - min));
    }

    public void calcularManhattan() {
        Map<String, List<PersonaSmartphoneAddiction>> profiles = null;
        List<ContextHistorySmartphoneUse> contextHistories = this.contextGenerator.fetchContextHistories();

        String distance = "";
        String finalCalc = "";
        String personasInfo = "";

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        Integer day = 0;
        Integer dayOfMonth = 0;
        List<ContextHistorySmartphoneUse> contextHistoryAcumulute = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");

        Double[] arrayMax = null;
        Double[] arrayMin = null;

        Integer counter = 0;
        for (ContextHistorySmartphoneUse contextHistory : contextHistories) {
            System.out.println(counter++ + "/" + contextHistories.size());
            Calendar cal = Calendar.getInstance();
            cal.setTime(contextHistory.getDateTime());
            if (dayOfMonth == 0) {
                dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
            }

            if (cal.get(Calendar.DAY_OF_MONTH) != dayOfMonth) {
                dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                day++;
                personasInfo += (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).
                        format(contextHistory.getDateTime()) + ";" + contextHistoryAcumulute.size() + ";" 
                        + this.personasGenerator.generatePersonas(contextHistoryAcumulute);
                //System.out.println((new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(contextHistory.getDateTime()) + ";" + contextHistoryAcumulute.size());
                //System.out.println("----------------------------");
                profiles = this.personasGenerator.fetchProfiles();
                arrayMax = fetchMaxValues();
                arrayMin = fetchMinValues();
            }

            contextHistoryAcumulute.add(contextHistory);

            if (null == profiles) {
                continue;
            }

            Double appMostUsedTimeInUse = contextHistory.getAppMostUsedTimeInUse().doubleValue();
            Double applicationUseTime = contextHistory.getApplicationUseTime().doubleValue();
            Double minutesUnlocked = contextHistory.getMinutesUnlocked().doubleValue();
            Double minutesLocked = contextHistory.getMinutesLocked().doubleValue();
            Double batteryLevel = contextHistory.getBatteryLevel();
            //powerEvent
            Double quantityNotifications = contextHistory.getQuantityNotifications().doubleValue();
            Double categoryNotificationsNumb = contextHistory.getCategoryNotificationsNumb().doubleValue();
            //mood
            Double sleepHoursEMA = contextHistory.getSleepHoursEMA().doubleValue();
            Double sleepRateEMA = contextHistory.getSleepRateEMA().doubleValue();
            Double stressLevelEMA = contextHistory.getStressLevelEMA().doubleValue();
            Double moodEMA = contextHistory.getMoodEMA().doubleValue();

            //DASS21
            Double stressScore = contextHistory.getStressScore().doubleValue();
            Double anxietyScore = contextHistory.getAnxietyScore().doubleValue();
            Double depressionScore = contextHistory.getDepressionScore().doubleValue();

            //Normalize Values
            Integer counterIndex = 0;
            anxietyScore = normalize(anxietyScore, arrayMin[counterIndex], arrayMax[counterIndex]);
            counterIndex++;
            appMostUsedTimeInUse = normalize(appMostUsedTimeInUse, arrayMin[counterIndex], arrayMax[counterIndex]);
            counterIndex++;
            applicationUseTime = normalize(applicationUseTime, arrayMin[counterIndex], arrayMax[counterIndex]);
            counterIndex++;
            batteryLevel = normalize(batteryLevel, arrayMin[counterIndex], arrayMax[counterIndex]);
            counterIndex++;
            categoryNotificationsNumb = normalize(categoryNotificationsNumb, arrayMin[counterIndex], arrayMax[counterIndex]);
            counterIndex++;
            depressionScore = normalize(depressionScore, arrayMin[counterIndex], arrayMax[counterIndex]);
            counterIndex++;
            minutesLocked = normalize(minutesLocked, arrayMin[counterIndex], arrayMax[counterIndex]);
            counterIndex++;
            minutesUnlocked = normalize(minutesUnlocked, arrayMin[counterIndex], arrayMax[counterIndex]);
            counterIndex++;
            moodEMA = normalize(moodEMA, arrayMin[counterIndex], arrayMax[counterIndex]);
            counterIndex++;
            quantityNotifications = normalize(quantityNotifications, arrayMin[counterIndex], arrayMax[counterIndex]);
            counterIndex++;
            sleepHoursEMA = normalize(sleepHoursEMA, arrayMin[counterIndex], arrayMax[counterIndex]);
            counterIndex++;
            sleepRateEMA = normalize(sleepRateEMA, arrayMin[counterIndex], arrayMax[counterIndex]);
            counterIndex++;
            stressLevelEMA = normalize(stressLevelEMA, arrayMin[counterIndex], arrayMax[counterIndex]);
            counterIndex++;
            stressScore = normalize(stressScore, arrayMin[counterIndex], arrayMax[counterIndex]);

            String keyAux = contextHistory.getPerson().getGender()
                    //+ ";" + contextHistory.getDayShift()
                    + ";" + contextHistory.getDayType()
                    + ";" + this.personasGenerator.categorizeAge(contextHistory.getPerson().getAge());

            List<PersonaSmartphoneAddiction> personas = profiles.get(keyAux);
            if (null == personas) {
                System.out.println(keyAux);
                personas = this.personasGenerator.fetchPersonas(
                        contextHistory.getPerson().getGender(),
                        this.personasGenerator.categorizeAge(contextHistory.getPerson().getAge()));
            }

            Map<PersonaSmartphoneAddiction, Double> dictionary = new HashMap<>();
            Map<PersonaSmartphoneAddiction, String> distances = new HashMap<>();

            for (PersonaSmartphoneAddiction persona : personas) {
                Double appsMostUsedTimeDistance = calcDistanceBack(appMostUsedTimeInUse,
                        persona.getAppMostUsedTimeInUse(), persona.getAppMostUsedTimeInUse());
                Double applicationUseTimeDistance = calcDistanceBack(applicationUseTime,
                        persona.getApplicationUseTime(), persona.getApplicationUseTime());
                Double minutesUnlockedDistance = calcDistanceBack(minutesUnlocked,
                        persona.getMinutesUnlocked(), persona.getMinutesUnlocked());
                Double minutesLockedDistance = calcDistanceBack(minutesLocked,
                        persona.getMinutesLocked(), persona.getMinutesLocked());
                Double batteryLevelDistance = calcDistanceBack(batteryLevel,
                        persona.getBatteryLevel(), persona.getBatteryLevel());

                Double quantityNotificationsDistance = calcDistanceBack(quantityNotifications,
                        persona.getQuantityNotifications(), persona.getQuantityNotifications());
                Double categoryNotificationsNumbDistance = calcDistanceBack(categoryNotificationsNumb,
                        persona.getCategoryNotificationsNumb(), persona.getCategoryNotificationsNumb());

                Double sleepHoursEMADistance = calcDistanceBack(sleepHoursEMA,
                        persona.getSleepHoursEMA(), persona.getSleepHoursEMA());
                Double sleepRateEMADistance = calcDistanceBack(sleepRateEMA,
                        persona.getSleepRateEMA(), persona.getSleepRateEMA());
                Double stressLevelEMADistance = calcDistanceBack(stressLevelEMA,
                        persona.getStressLevelEMA(), persona.getStressLevelEMA());

                Double moodEMADistance = 0.0;
                try {
                    moodEMADistance = calcDistanceBack(moodEMA,
                            persona.getMoodEMA(), persona.getMoodEMA());
                } catch (Exception e) {
                    System.out.println("error");
                }

                Double stressScoreDistance = calcDistanceBack(stressScore,
                        persona.getStressScore(), persona.getStressScore());
                Double anxietyScoreDistance = calcDistanceBack(anxietyScore,
                        persona.getAnxietyScore(), persona.getAnxietyScore());
                Double depressionScoreDistance = calcDistanceBack(depressionScore,
                        persona.getDepressionScore(), persona.getDepressionScore());

//                System.out.println(anxietyScoreDistance + ";"
//                        + applicationUseTimeDistance + ";"
//                        + appsMostUsedTimeDistance + ";"
//                        + batteryLevelDistance + ";"
//                        + categoryNotificationsNumbDistance + ";"
//                        + depressionScoreDistance + ";"
//                        + minutesLockedDistance + ";"
//                        + minutesUnlockedDistance + ";"
//                        + moodEMADistance + ";"
//                        + quantityNotificationsDistance + ";"
//                        + sleepHoursEMADistance + ";"
//                        + sleepRateEMADistance + ";"
//                        + stressLevelEMADistance + ";"
//                        + stressScoreDistance);
//                Double[] dist = {
//                    anxietyScoreDistance / maxValues[counterIndex++],
//                    applicationUseTimeDistance / maxValues[counterIndex++],
//                    appsMostUsedTimeDistance / maxValues[counterIndex++],
//                    batteryLevelDistance / maxValues[counterIndex++],
//                    categoryNotificationsNumbDistance / maxValues[counterIndex++],
//                    depressionScoreDistance / maxValues[counterIndex++],
//                    minutesLockedDistance / maxValues[counterIndex++],
//                    minutesUnlockedDistance / maxValues[counterIndex++],
//                    //moodEMADistance / maxValues[counterIndex++],
//                    quantityNotificationsDistance / maxValues[counterIndex++],
//                    sleepHoursEMADistance / maxValues[counterIndex++],
//                    //sleepRateEMADistance / maxValues[counterIndex++],
//                    //stressLevelEMADistance / maxValues[counterIndex++],
//                    stressScoreDistance / maxValues[counterIndex++]};
                Double[] dist = {
                    anxietyScoreDistance,
                    applicationUseTimeDistance,
                    appsMostUsedTimeDistance,
                    batteryLevelDistance,
                    categoryNotificationsNumbDistance,
                    depressionScoreDistance,
                    minutesLockedDistance,
                    minutesUnlockedDistance,
                    moodEMADistance,
                    quantityNotificationsDistance,
                    sleepHoursEMADistance,
                    sleepRateEMADistance,
                    stressLevelEMADistance,
                    stressScoreDistance};

                Double total = 0.0;
                for (int i = 0; i < dist.length; i++) {
                    //System.out.println(distancia[i]);
                    //distance += dist[i] + ";";
                    total += dist[i];
                }
                dictionary.put(persona, total);

                String distAux = "";
                for (int i = 0; i < dist.length; i++) {
                    distAux += df.format(dist[i]);
                    if ((i + 1) < dist.length) {
                        distAux += "; ";
                    }
                }

                distances.put(persona, distAux);
            }

            dictionary = sortByValue(dictionary);

            for (Entry<PersonaSmartphoneAddiction, Double> entry : dictionary.entrySet()) {
                PersonaSmartphoneAddiction key = entry.getKey();
                Double value = entry.getValue();
                if (key.getTypeUser().equalsIgnoreCase("Smartphone Addicted Behavior")) {
                    contextHistory.setDistanceAddicted(value);
                } else {
                    contextHistory.setDistanceNormal(value);
                }
            }

            Map<PersonaSmartphoneAddiction, Double> output = analyzeTargetObj(dictionary);
            List<Map.Entry<PersonaSmartphoneAddiction, Double>> list = new ArrayList<>(output.entrySet());
            String max = "";
            Integer trigger = 0;
            for (Entry<PersonaSmartphoneAddiction, Double> entry : output.entrySet()) {
                PersonaSmartphoneAddiction key = entry.getKey();
                Double value = entry.getValue();

                try {
                    distance += distances.get(key) + "\n";
                } catch (Exception e) {
                    System.out.println(e);
                }

                //finalCalc += (contextHistory.getId() + ";" + contextHistory.getDateTime() + ";" + contextHistory.getSmartphoneAddicted() + ";" + key.getTypeUser() + ";" + value + "\n");
                if (trigger++ == 0) {
                    max = key.getTypeUser() + ";" + value.toString().replace(".", ",");
//                    System.out.println(contextHistory.getId()
//                            + ";" + contextHistory.getDateTime()
//                            + ";" + contextHistory.getSmartphoneAddicted()
//                            + ";" + key.getTypeUser() + ";" + ";" + value);
                    contextHistory.setKnnPrediction(key.getTypeUser());
                    contextHistory.setKnnPredictionRating(value);

                    if (key.getTypeUser().equalsIgnoreCase("Smartphone Addicted Behavior")) {
                        contextHistory.setSuggestedIntervention(suggestIntervention(contextHistory));
                    }

                    finalCalc += contextHistory.getId()
                            + ";" + contextHistory.getDateTime()
                            + ";" + contextHistory.getSmartphoneAddicted()
                            + ";" + key.getTypeUser() + ";" + ";" + value + "\n";
                }

                em.merge(contextHistory);

                if (counter % 1000 == 0) {
                    em.flush();
                    em.clear();
                }

//                System.out.println(contextHistory.getId()
//                        + ";" + contextHistory.getDateTime()
//                        + ";" + contextHistory.getSmartphoneAddicted()
//                        + ";" + key.getTypeUser() + ";" + ";" + value);
            }
        }
        System.out.println("---------------------------");
        System.out.println(finalCalc);
        System.out.println("---------------------------");
        System.out.println(personasInfo);
        System.out.println("---------------------------");
        System.out.println(distance);

        em.getTransaction().commit();
        em.close();
    }

    private String suggestIntervention(ContextHistorySmartphoneUse context) {
        //Preventive
        // Mindfulness
        // PhysicalExercises
        // SleepProcedures
        //Reactive
        // AppDelay
        // GreyScaleDisplay
        // NotificationDisable
        // SocialMediaDisabling

        List<String> inverventions = new ArrayList<>();

        if (context.getDayShift().equalsIgnoreCase("Night")) {
            //Sleep Procedures
            // Mindfulness
            inverventions.add("Sleep Procedures");
            inverventions.add("Mindfulness");
        }

        if (!context.getDayShift().equalsIgnoreCase("Night") && context.getDayType().equalsIgnoreCase("Weekend")) {
            // PhysicalExercises
            inverventions.add("Physical Exercises");
        }

        if (context.getDayShift().equalsIgnoreCase("Evening") && context.getDayType().equalsIgnoreCase("Weekday")) {
            // PhysicalExercises
            inverventions.add("Physical Exercises");
        }

        if (context.getCategoryNotificationsNumb() > 40) {
            //NotificationDisable
            inverventions.add("Notification Disable");
        }

        if (context.getApplicationTopUse().equalsIgnoreCase("Social")) {
            //SocialMediaDisabling
            inverventions.add("Social Media Disabling");
        }

        //AppDelay
        inverventions.add("App Delay");

        //GreyScaleDisplay
        inverventions.add("Grey Scale Display");

        Random rand = new Random();
        Integer max = inverventions.size() - 1;

        Integer n = rand.nextInt(max);
        return inverventions.get(n);
    }

    private Double calcDistanceBack(Double a, Double b, Double c) {
        Double d;
        if (a > c) {
            d = a.doubleValue() - c;
        } else if (a < b) {
            d = b.doubleValue() - a;
        } else {
            d = 0.0;
        }
        return d;
    }

    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private Map<PersonaSmartphoneAddiction, Double> analyzeTargetObj(Map<PersonaSmartphoneAddiction, Double> dictionary) {
        Map<PersonaSmartphoneAddiction, Double> dictionaryFilter = new HashMap<>();
        Map<PersonaSmartphoneAddiction, Double> percentuais = new HashMap<>();
        Map<PersonaSmartphoneAddiction, Double> outputMap = new HashMap<>();
        Integer max = 4;
        Boolean containsZero = Boolean.FALSE;
        for (Entry<PersonaSmartphoneAddiction, Double> entry : dictionary.entrySet()) {
            if (max == 0) {
                break;
            }
            PersonaSmartphoneAddiction key = entry.getKey();
            Double value = entry.getValue();
            dictionaryFilter.put(key, value);
            max--;
            if (value.intValue() == 0) {
                if (Objects.equals(containsZero, Boolean.FALSE)) {
                    percentuais.put(key, 1.0);
                } else {
                    percentuais.put(key, 0.0);
                }
                containsZero = Boolean.TRUE;
            }
        }

        if (!containsZero) {
            for (Entry<PersonaSmartphoneAddiction, Double> entry : dictionaryFilter.entrySet()) {
                PersonaSmartphoneAddiction key = entry.getKey();
                Double value = entry.getValue();
                percentuais.put(key, 1 / value);
            }
        }

        for (Entry<PersonaSmartphoneAddiction, Double> entry : percentuais.entrySet()) {
            PersonaSmartphoneAddiction key = entry.getKey();
            Double value = entry.getValue();
            outputMap.put(key, value / sumProbObject(percentuais));
        }

        outputMap = sortByValue(outputMap);
//        for (Entry<PersonaSmartphoneAddiction, Double> entry : outputMap.entrySet()) {
//            PersonaSmartphoneAddiction key = entry.getKey();
//        }
        //System.out.println(documento + out + number);
        return outputMap;
    }

    private Double sumProbObject(Map<PersonaSmartphoneAddiction, Double> percentuais) {
        Double output = 0.0;
        for (Entry<PersonaSmartphoneAddiction, Double> entry : percentuais.entrySet()) {
            output += entry.getValue();
        }
        return output;
    }

    private Double[] fetchMaxValues() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery(
                /*0*/"SELECT MAX(i.anxietyScore), "
                /*1*/ + "MAX(i.appMostUsedTimeInUse), "
                /*2*/ + "MAX(i.applicationUseTime), "
                /*3*/ + "MAX(i.batteryLevel), "
                /*4*/ + "MAX(i.categoryNotificationsNumb), "
                /*5*/ + "MAX(i.depressionScore), "
                /*6*/ + "MAX(i.minutesLocked), "
                /*7*/ + "MAX(i.minutesUnlocked), "
                /*8*/ + "MAX(i.moodEMA), "
                /*9*/ + "MAX(i.quantityNotifications), "
                /*10*/ + "MAX(i.sleepHoursEMA), "
                /*11*/ + "MAX(i.sleepRateEMA), "
                /*12*/ + "MAX(i.stressLevelEMA), "
                /*13*/ + "MAX(i.stressScore) "
                + "FROM ContextHistorySmartphoneUse i");
        Object[] maxValues = (Object[]) query.getResultList().get(0);
        em.close();
        Double[] outputMaxValues = new Double[maxValues.length];
        for (int i = 0; i < maxValues.length; i++) {
            if (maxValues[i] instanceof Double) {
                outputMaxValues[i] = ((Double) maxValues[i]);
            } else {
                outputMaxValues[i] = ((Integer) maxValues[i]).doubleValue();
            }
        }
        return outputMaxValues;
    }

    private Double[] fetchMinValues() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery(
                /*0*/"SELECT MIN(i.anxietyScore), "
                /*1*/ + "MIN(i.appMostUsedTimeInUse), "
                /*2*/ + "MIN(i.applicationUseTime), "
                /*3*/ + "MIN(i.batteryLevel), "
                /*4*/ + "MIN(i.categoryNotificationsNumb), "
                /*5*/ + "MIN(i.depressionScore), "
                /*6*/ + "MIN(i.minutesLocked), "
                /*7*/ + "MIN(i.minutesUnlocked), "
                /*8*/ + "MIN(i.moodEMA), "
                /*9*/ + "MIN(i.quantityNotifications), "
                /*10*/ + "MIN(i.sleepHoursEMA), "
                /*11*/ + "MIN(i.sleepRateEMA), "
                /*12*/ + "MIN(i.stressLevelEMA), "
                /*13*/ + "MIN(i.stressScore) "
                + "FROM ContextHistorySmartphoneUse i");
        Object[] maxValues = (Object[]) query.getResultList().get(0);
        em.close();
        Double[] outputMaxValues = new Double[maxValues.length];
        for (int i = 0; i < maxValues.length; i++) {
            if (maxValues[i] instanceof Double) {
                outputMaxValues[i] = ((Double) maxValues[i]);
            } else {
                outputMaxValues[i] = ((Integer) maxValues[i]).doubleValue();
            }
        }
        return outputMaxValues;
    }
    
    public void categorizeAge(){
        List<ContextHistorySmartphoneUse> contextHistories = this.contextGenerator.fetchContextHistories();
        
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        for (ContextHistorySmartphoneUse contextHistory : contextHistories) {
            contextHistory.setAgeCategory(this.personasGenerator.categorizeAge(contextHistory.getPerson().getAge()));
            em.merge(contextHistory);
        }
        em.getTransaction().commit();
        em.close();
    }

}
