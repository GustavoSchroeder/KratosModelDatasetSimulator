package br.unisinos.model;

import br.unisinos.model.generator.ContextGenerator;
import br.unisinos.model.generator.GeneratePersonas;
import br.unisinos.pojo.ContextHistorySmartphoneUse;
import br.unisinos.pojo.PersonaSmartphoneAddiction;
import br.unisinos.util.JPAUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
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

    public void calcularManhattan() {
        Map<String, List<PersonaSmartphoneAddiction>> profiles = this.personasGenerator.fetchProfiles();
        List<ContextHistorySmartphoneUse> contextHistories = this.contextGenerator.fetchContextHistories();

        String distance = "";
        String finalCalc = "";

        Integer[] maxValues = fetchMaxValues();

        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        for (ContextHistorySmartphoneUse contextHistory : contextHistories) {
            Integer appMostUsedTimeInUse = contextHistory.getAppMostUsedTimeInUse();
            Integer applicationUseTime = contextHistory.getApplicationUseTime();
            Integer minutesUnlocked = contextHistory.getMinutesUnlocked();
            Integer minutesLocked = contextHistory.getMinutesLocked();
            Double batteryLevel = contextHistory.getBatteryLevel();
            //powerEvent
            Integer quantityNotifications = contextHistory.getQuantityNotifications();
            Integer categoryNotificationsNumb = contextHistory.getCategoryNotificationsNumb();
            //mood
            Integer sleepHoursEMA = contextHistory.getSleepHoursEMA();
            Integer sleepRateEMA = contextHistory.getSleepRateEMA();
            Integer stressLevelEMA = contextHistory.getStressLevelEMA();
            Integer moodEMA = contextHistory.getMoodEMA();

            //DASS21
            Integer stressScore = contextHistory.getStressScore();
            Integer anxietyScore = contextHistory.getAnxietyScore();
            Integer depressionScore = contextHistory.getDepressionScore();

            String keyAux = contextHistory.getPerson().getGender()
                    + ";" + contextHistory.getDayShift()
                    + ";" + this.personasGenerator.categorizeAge(contextHistory.getPerson().getAge());

            List<PersonaSmartphoneAddiction> personas = profiles.get(keyAux);

            Map<PersonaSmartphoneAddiction, Double> dictionary = new HashMap<>();

            for (PersonaSmartphoneAddiction persona : personas) {
                Double appsMostUsedTimeDistance = calcDistanceBack(appMostUsedTimeInUse,
                        persona.getAppMostUsedTimeInUse(), persona.getAppMostUsedTimeInUse());
                Double applicationUseTimeDistance = calcDistanceBack(applicationUseTime,
                        persona.getApplicationUseTime(), persona.getApplicationUseTime());
                Double minutesUnlockedDistance = calcDistanceBack(minutesUnlocked,
                        persona.getMinutesUnlocked(), persona.getMinutesUnlocked());
                Double minutesLockedDistance = calcDistanceBack(minutesLocked,
                        persona.getMinutesLocked(), persona.getMinutesLocked());
                Double batteryLevelDistance = calcDistanceBack(batteryLevel.intValue(),
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
                Integer counterIndex = 0;
                Double[] dist = {
                    anxietyScoreDistance / maxValues[counterIndex++],
                    applicationUseTimeDistance / maxValues[counterIndex++],
                    appsMostUsedTimeDistance / maxValues[counterIndex++],
                    batteryLevelDistance / maxValues[counterIndex++],
                    categoryNotificationsNumbDistance / maxValues[counterIndex++],
                    depressionScoreDistance / maxValues[counterIndex++],
                    minutesLockedDistance / maxValues[counterIndex++],
                    minutesUnlockedDistance / maxValues[counterIndex++],
                    moodEMADistance / maxValues[counterIndex++],
                    quantityNotificationsDistance / maxValues[counterIndex++],
                    sleepHoursEMADistance / maxValues[counterIndex++],
                    sleepRateEMADistance / maxValues[counterIndex++],
                    stressLevelEMADistance / maxValues[counterIndex++],
                    stressScoreDistance / maxValues[counterIndex++]};

                Double total = 0.0;
                for (int i = 0; i < dist.length; i++) {
                    //System.out.println(distancia[i]);
                    distance += dist[i] + ";";
                    total += dist[i];
                }
                dictionary.put(persona, total);
            }

            dictionary = sortByValue(dictionary);
            Map<PersonaSmartphoneAddiction, Double> output = analyzeTargetObj(dictionary);
            List<Map.Entry<PersonaSmartphoneAddiction, Double>> list = new ArrayList<>(output.entrySet());
            String max = "";
            Integer trigger = 0;
            for (Entry<PersonaSmartphoneAddiction, Double> entry : output.entrySet()) {
                PersonaSmartphoneAddiction key = entry.getKey();
                Double value = entry.getValue();
                //finalCalc += (contextHistory.getId() + ";" + contextHistory.getDateTime() + ";" + contextHistory.getSmartphoneAddicted() + ";" + key.getTypeUser() + ";" + value + "\n");
                if (trigger++ == 0) {
                    max = key.getTypeUser() + ";" + value.toString().replace(".", ",");
//                    System.out.println(contextHistory.getId()
//                            + ";" + contextHistory.getDateTime()
//                            + ";" + contextHistory.getSmartphoneAddicted()
//                            + ";" + key.getTypeUser() + ";" + ";" + value);
                    contextHistory.setKnnPrediction(key.getTypeUser());
                    contextHistory.setKnnPredictionRating(value);
                    contextHistory.setKnnPredictionProfile(key);
                    em.merge(contextHistory);

                    finalCalc += contextHistory.getId()
                            + ";" + contextHistory.getDateTime()
                            + ";" + contextHistory.getSmartphoneAddicted()
                            + ";" + key.getTypeUser() + ";" + ";" + value + "\n";
                }
//                System.out.println(contextHistory.getId()
//                        + ";" + contextHistory.getDateTime()
//                        + ";" + contextHistory.getSmartphoneAddicted()
//                        + ";" + key.getTypeUser() + ";" + ";" + value);
            }
        }
        System.out.println("---------------------------");
        System.out.println(finalCalc);

        em.getTransaction().commit();
        em.close();
    }

    private Double calcDistanceBack(Integer a, Integer b, Integer c) {
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
        for (Entry<PersonaSmartphoneAddiction, Double> entry : outputMap.entrySet()) {
            PersonaSmartphoneAddiction key = entry.getKey();
        }
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

    private Integer[] fetchMaxValues() {
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
                /*13*/ + "MAX(i.stressScore) FROM ContextHistorySmartphoneUse i");
        Object[] maxValues = (Object[]) query.getResultList().get(0);
        em.close();
        Integer[] outputMaxValues = new Integer[maxValues.length];
        for (int i = 0; i < maxValues.length; i++) {
            if (maxValues[i] instanceof Double) {
                outputMaxValues[i] = ((Double) maxValues[i]).intValue();
            } else {
                outputMaxValues[i] = ((Integer) maxValues[i]);
            }

        }
        return outputMaxValues;
    }

}
