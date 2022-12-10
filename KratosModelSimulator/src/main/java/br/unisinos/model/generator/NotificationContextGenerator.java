/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model.generator;

import br.unisinos.pojo.ContextInformation.Notification;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.TimeUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class NotificationContextGenerator {

    private TimeUtil timeUtil;

    public NotificationContextGenerator() {
        this.timeUtil = new TimeUtil();
    }

    public Map<String, Map<String, Map<Integer, List<Notification>>>> generatePowerEventInformation(Long idPerson) {
        List<Notification> notificationList = fetchNotificationInformation(idPerson);

        //TypeDay -> Day -> Hour -> phoneCharge
        Map<String, Map<String, Map<Integer, List<Notification>>>> mapNotification = new HashMap<>();
        for (Notification notification : notificationList) {

            String dayType = this.timeUtil.checkWeekDay(notification.getDatePosted());
            Calendar startDate = Calendar.getInstance();
            startDate.setTime(notification.getDatePosted());

            if (null == mapNotification.get(dayType)) {
                mapNotification.put(dayType, new HashMap<>());
            }

            String day = startDate.get(Calendar.DAY_OF_MONTH) + "/" + startDate.get(Calendar.MONTH);
            if (null == mapNotification.get(dayType).get(day)) {
                mapNotification.get(dayType).put(day, new HashMap<>());
            }

            if (null == mapNotification.get(dayType).get(day).get(startDate.get(Calendar.HOUR_OF_DAY))) {
                mapNotification.get(dayType).get(day).put(startDate.get(Calendar.HOUR_OF_DAY), new ArrayList<>());
            }

            mapNotification.get(dayType).get(day).get(startDate.get(Calendar.HOUR_OF_DAY)).add(notification);
        }

        return mapNotification;
    }

    public Object[] buildNotificationInfo(String dayType, Integer hour,
            Map<String, Map<String, Map<Integer, List<Notification>>>> mapNotification) {
        Map<Integer, List<Notification>> notificationsByHour = fetchRandomNotificationBehavior(dayType, mapNotification);
        List<Notification> notifications = notificationsByHour.get(hour);
        if(null == notifications){
            Object[] output = {0, "-", 0};
            return output;
        }
        Integer numberNotifications = notifications.size();
        Map<String, Integer> dictionaryCategory = new HashMap<>();
        for (Notification notification : notifications) {
            if(null == dictionaryCategory.get(notification.getPackageCategory())){
            dictionaryCategory.put(notification.getPackageCategory(), 0);
            }
            dictionaryCategory.put(notification.getPackageCategory(), 
                    dictionaryCategory.get(notification.getPackageCategory()) + 1);
        }
        
        Integer max = 0;
        String categoryMax = "";
        for (Entry<String, Integer> entry : dictionaryCategory.entrySet()) {
            String key = entry.getKey();
            Integer val = entry.getValue();
            
            if(val > max){
                max = val;
                categoryMax = key;
            }
        }
        
        Object[] output = {numberNotifications, categoryMax, max};
        return output;
    }

    public Map<Integer, List<Notification>> fetchRandomNotificationBehavior(String dayType,
            Map<String, Map<String, Map<Integer, List<Notification>>>> mapNotification) {

        Map<String, Map<Integer, List<Notification>>> notificationDay = mapNotification.get(dayType);

        List<String> keySet = new ArrayList<>(notificationDay.keySet());
        Random rand = new Random();
        Integer n = rand.nextInt((keySet.size()));

        return notificationDay.get(keySet.get(n));
    }

    private List<Notification> fetchNotificationInformation(Long idPerson) {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM Notification i WHERE i.person.id = :idPerson");
        query.setParameter("idPerson", idPerson);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}
