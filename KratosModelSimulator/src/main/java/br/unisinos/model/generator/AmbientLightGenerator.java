/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model.generator;

import br.unisinos.pojo.ContextInformation.TimeDarkEnvironment;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.TimeUtil;
import java.util.ArrayList;
import java.util.Calendar;
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
public class AmbientLightGenerator {

    private TimeUtil timeUtil;

    public AmbientLightGenerator() {
        this.timeUtil = new TimeUtil();
    }

    public Map<String, Map<String, Map<Integer, String>>> generateLightInfo(Long idPerson) {
        List<TimeDarkEnvironment> listDark = fetchTimeDarkEnvironment(idPerson);

        if (listDark.isEmpty()) {
            listDark = fetchRandomTimeDarkEnvironment();
        }

        Map<String, Map<String, Map<Integer, String>>> mapLight = new HashMap<>();

        for (TimeDarkEnvironment dark : listDark) {

            Calendar startDate = Calendar.getInstance();
            startDate.setTime(dark.getStartDate());

            Calendar endDate = Calendar.getInstance();
            endDate.setTime(dark.getEndDate());

            String start = "";
            String end = "";
            do {
                if (endDate.before(startDate)) {
                    break;
                }

                String dayType = this.timeUtil.checkWeekDay(dark.getStartDate());

                if (null == mapLight.get(dayType)) {
                    mapLight.put(dayType, new HashMap<>());
                }

                String day = startDate.get(Calendar.DAY_OF_MONTH) + "/" + startDate.get(Calendar.MONTH);
                if (null == mapLight.get(dayType).get(day)) {
                    mapLight.get(dayType).put(day, new HashMap<>());
                }

                if (null == mapLight.get(dayType).get(day).get(startDate.get(Calendar.HOUR_OF_DAY))) {
                    mapLight.get(dayType).get(day).put(startDate.get(Calendar.HOUR_OF_DAY), "Dark");
                }

                startDate.add(Calendar.HOUR_OF_DAY, 1);

                start = (startDate.get(Calendar.DAY_OF_MONTH) + ""
                        + startDate.get(Calendar.MONTH)
                        + startDate.get(Calendar.YEAR) + startDate.get(Calendar.HOUR_OF_DAY)
                        + "");
                end = endDate.get(Calendar.DAY_OF_MONTH) + ""
                        + endDate.get(Calendar.MONTH)
                        + endDate.get(Calendar.YEAR)
                        + endDate.get(Calendar.HOUR_OF_DAY) + "";

                //System.out.println(start + ";" + end);
            } while (!start.equalsIgnoreCase(end));
        }

        for (Map.Entry<String, Map<String, Map<Integer, String>>> entry : mapLight.entrySet()) {
            String key = entry.getKey();
            Map<String, Map<Integer, String>> value = entry.getValue();
            for (Map.Entry<String, Map<Integer, String>> entry1 : value.entrySet()) {
                String key1 = entry1.getKey();
                Map<Integer, String> value1 = entry1.getValue();
                for (int i = 0; i < 24; i++) {
                    if (null == mapLight.get(key).get(key1).get(i)) {
                        if (i > 20 || i < 6) {
                            mapLight.get(key).get(key1).put(i, "Dark");
                        } else {
                            mapLight.get(key).get(key1).put(i, "Bright");
                        }
                    }
                }
            }
        }

        return mapLight;
    }

    public Map<Integer, String> fetchRandomDayAmbientLight(String dayType,
            Map<String, Map<String, Map<Integer, String>>> mapDark) {

        Map<String, Map<Integer, String>> mapDays = mapDark.get(dayType);

        List<String> keySet = new ArrayList<>(mapDays.keySet());
        Random rand = new Random();
        Integer n = rand.nextInt((keySet.size()));

        return mapDays.get(keySet.get(n));
    }

    private List<TimeDarkEnvironment> fetchTimeDarkEnvironment(Long idPerson) {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM TimeDarkEnvironment i WHERE i.person.id = :idPerson");
        query.setParameter("idPerson", idPerson);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    private List<TimeDarkEnvironment> fetchRandomTimeDarkEnvironment() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT DISTINCT(i.person.id) FROM TimeDarkEnvironment i");
        try {
            List<Long> ids = query.getResultList();
            Random rand = new Random();
            Integer n = rand.nextInt((ids.size()));
            return fetchTimeDarkEnvironment(ids.get(n));
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}
