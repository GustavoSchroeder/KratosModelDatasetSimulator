/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model.generator;

import br.unisinos.pojo.ContextInformation.Notification;
import br.unisinos.pojo.EMA.MoodEMA;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.TimeUtil;
import java.util.ArrayList;
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
public class EMAGenerator {

    private TimeUtil timeUtil;

    public EMAGenerator() {
        this.timeUtil = new TimeUtil();
    }

    public Integer fetchRandomEMA(String dayType, Map<String, List<Integer>> dictionary) {

        List<Integer> listEMA = dictionary.get(dayType);

        Random rand = new Random();
        Integer n = rand.nextInt((listEMA.size()));

        return listEMA.get(n);
    }

    //DayType --> Happy or Not
    public Map<String, List<Integer>> createDictionaryMood() {
        List<MoodEMA> listMood = findMoodEMA();
        Map<String, List<Integer>> dictionary = new HashMap<>();

        for (MoodEMA moodEMA : listMood) {
            String dayType = this.timeUtil.checkWeekDay(moodEMA.getResponseDate());

            if (null == dictionary.get(dayType)) {
                dictionary.put(dayType, new ArrayList<>());
            }

            dictionary.get(dayType).add(moodEMA.getHappyOrNot());
        }

        return dictionary;
    }

    private List<MoodEMA> findMoodEMA() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM MoodEMA i");

        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

}
