/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model.generator;

import br.unisinos.pojo.ContextInformation.PhoneCharge;
import br.unisinos.pojo.ContextInformation.PhoneLock;
import br.unisinos.pojo.Person;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.TimeUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
public class DeviceInformationGenerator {

    private TimeUtil timeUtil;

    public DeviceInformationGenerator() {
        this.timeUtil = new TimeUtil();
    }

    //Phone Charge
    public Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>>> organizePowerEvents(List<Person> persons) {
        Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>>> dictionaryPhoneCharge = new HashMap<>();

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

        for (Person person : persons) {
            List<PhoneCharge> phoneChargeList = fetchPowerEvent(person.getId());

            Map<String, Integer> dictionaryDate = new HashMap<>();
            Integer indexDate = 1;

            if (null == dictionaryPhoneCharge.get(person.getId())) {
                dictionaryPhoneCharge.put(person.getId(), new HashMap<>());
            }

            for (PhoneCharge phoneCharge : phoneChargeList) {

                String dayType = this.timeUtil.checkWeekDay(phoneCharge.getStartDate());

                if (null == dictionaryPhoneCharge.get(person.getId()).get(dayType)) {
                    dictionaryPhoneCharge.get(person.getId()).put(dayType, new HashMap<>());
                }

                String auxDate = sdfDate.format(phoneCharge.getStartDate());
                if (null == dictionaryDate.get(auxDate)) {
                    dictionaryDate.put(auxDate, indexDate++);
                }

                Integer iDate = dictionaryDate.get(auxDate);
                if (null == dictionaryPhoneCharge.get(person.getId()).get(dayType).get(iDate)) {
                    dictionaryPhoneCharge.get(person.getId()).get(dayType).put(iDate, new HashMap<>());
                }

                Integer hour = this.timeUtil.fetchHour(phoneCharge.getStartDate());
                if (null == dictionaryPhoneCharge.get(person.getId()).get(dayType).get(iDate).get(hour)) {
                    dictionaryPhoneCharge.get(person.getId()).get(dayType).get(iDate).put(hour, new ArrayList<>());
                }
                dictionaryPhoneCharge.get(person.getId()).get(dayType).get(iDate).get(hour).add(phoneCharge);
            }
        }
        dictionaryPhoneCharge = fillEmptyUserIds(dictionaryPhoneCharge);
        return dictionaryPhoneCharge;
    }

    private Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>>> fillEmptyUserIds(
            Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>>> dictionaryPhoneCharge) {
        List<Long> emptyIds = new ArrayList<>();
        List<Long> notEmptyIds = new ArrayList<>();

        for (Map.Entry<Long, Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>>> entry : dictionaryPhoneCharge.entrySet()) {
            Long key = entry.getKey();

            Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>> value = entry.getValue();
            if (value.isEmpty()) {
                emptyIds.add(key);
            } else {
                notEmptyIds.add(key);
            }
        }

        Random rand = new Random();
        for (Long emptyId : emptyIds) {
            Integer n = rand.nextInt((notEmptyIds.size() - 1));
            dictionaryPhoneCharge.put(emptyId, dictionaryPhoneCharge.get(notEmptyIds.get(n)));
        }

        return dictionaryPhoneCharge;
    }

    public List<PhoneCharge> fetchPowerEvent(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM PhoneCharge i WHERE i.person.id = :id");
        query.setParameter("id", id);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    //Phone Lock
    public Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneLock>>>>> organizeScreenStatus(List<Person> persons) {
        Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneLock>>>>> dictionaryScreenStatus = new HashMap<>();

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");

        for (Person person : persons) {
            List<PhoneLock> phoneLockList = fetchScreenStatus(person.getId());

            Map<String, Integer> dictionaryDate = new HashMap<>();
            Integer indexDate = 1;

            if (null == dictionaryScreenStatus.get(person.getId())) {
                dictionaryScreenStatus.put(person.getId(), new HashMap<>());
            }

            for (PhoneLock phoneLock : phoneLockList) {

                String dayType = this.timeUtil.checkWeekDay(phoneLock.getStartDate());

                if (null == dictionaryScreenStatus.get(person.getId()).get(dayType)) {
                    dictionaryScreenStatus.get(person.getId()).put(dayType, new HashMap<>());
                }

                String auxDate = sdfDate.format(phoneLock.getStartDate());
                if (null == dictionaryDate.get(auxDate)) {
                    dictionaryDate.put(auxDate, indexDate++);
                }

                Integer iDate = dictionaryDate.get(auxDate);
                if (null == dictionaryScreenStatus.get(person.getId()).get(dayType).get(iDate)) {
                    dictionaryScreenStatus.get(person.getId()).get(dayType).put(iDate, new HashMap<>());
                }

                Integer hour = this.timeUtil.fetchHour(phoneLock.getStartDate());
                if (null == dictionaryScreenStatus.get(person.getId()).get(dayType).get(iDate).get(hour)) {
                    dictionaryScreenStatus.get(person.getId()).get(dayType).get(iDate).put(hour, new ArrayList<>());
                }
                dictionaryScreenStatus.get(person.getId()).get(dayType).get(iDate).get(hour).add(phoneLock);
            }
        }

        dictionaryScreenStatus = fillEmptyPhoneLockUserIds(dictionaryScreenStatus);
        return dictionaryScreenStatus;
    }

    private Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneLock>>>>> fillEmptyPhoneLockUserIds(
            Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneLock>>>>> dictionaryPhoneLock) {
        List<Long> emptyIds = new ArrayList<>();
        List<Long> notEmptyIds = new ArrayList<>();

        for (Map.Entry<Long, Map<String, Map<Integer, Map<Integer, List<PhoneLock>>>>> entry : dictionaryPhoneLock.entrySet()) {
            Long key = entry.getKey();

            Map<String, Map<Integer, Map<Integer, List<PhoneLock>>>> value = entry.getValue();
            if (value.isEmpty()) {
                emptyIds.add(key);
            } else {
                notEmptyIds.add(key);
            }
        }

        Random rand = new Random();
        for (Long emptyId : emptyIds) {
            Integer n = rand.nextInt((notEmptyIds.size() - 1));
            dictionaryPhoneLock.put(emptyId, dictionaryPhoneLock.get(notEmptyIds.get(n)));
        }

        return dictionaryPhoneLock;
    }

    public List<Integer[]> randomDayPhoneLock(Map<Integer, Map<Integer, List<PhoneLock>>> dictionary) {

        List<Integer> keySet = new ArrayList<>(dictionary.keySet());
        Random rand = new Random();
        Integer n = rand.nextInt((keySet.size() - 1));

        List<Integer[]> hoursPhoneLock = new ArrayList<>();
        
        Map<Integer, List<PhoneLock>> dictionaryOut = dictionary.get(keySet.get(n));
        for (Map.Entry<Integer, List<PhoneLock>> entry : dictionaryOut.entrySet()) {
            List<PhoneLock> val = entry.getValue();

            for (PhoneLock phoneLock : val) {
                Calendar initialDate = Calendar.getInstance();
                initialDate.setTime(phoneLock.getStartTime());
                Calendar finalDate = Calendar.getInstance();
                finalDate.setTime(phoneLock.getEndTime());
                
                Integer[] payload = {initialDate.get(Calendar.HOUR), finalDate.get(Calendar.HOUR)};
                hoursPhoneLock.add(payload);
            }
        }
        return hoursPhoneLock;
    }

    public List<PhoneLock> fetchScreenStatus(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM PhoneLock i WHERE i.person.id = :id");
        query.setParameter("id", id);
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

    //Test
    public void generateSmartphoneBattery(
            Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>>> dictionaryPhoneCharge) {
        Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>>> dictionaryBattery = new HashMap<>();

        for (Map.Entry<Long, Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>>> entry : dictionaryBattery.entrySet()) {
            Long key = entry.getKey();
            Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>> value = entry.getValue();
            dictionaryBattery.put(key, new HashMap<>());

            for (Map.Entry<String, Map<Integer, Map<Integer, List<PhoneCharge>>>> entry1 : value.entrySet()) {
                String key1 = entry1.getKey();
                Map<Integer, Map<Integer, List<PhoneCharge>>> value1 = entry1.getValue();
                dictionaryBattery.get(key).put(key1, new HashMap<>());

                for (Map.Entry<Integer, Map<Integer, List<PhoneCharge>>> entry2 : value1.entrySet()) {
                    Integer key2 = entry2.getKey();
                    Map<Integer, List<PhoneCharge>> value2 = entry2.getValue();
                    dictionaryBattery.get(key).get(key1).put(key2, new HashMap<>());

                    List<Integer> hoursCharged = new ArrayList<>();
                    for (Map.Entry<Integer, List<PhoneCharge>> entry3 : value2.entrySet()) {
                        Integer key3 = entry3.getKey();
                        List<PhoneCharge> value3 = entry3.getValue();

                        for (PhoneCharge phoneCharge : value3) {
                            Calendar cal = Calendar.getInstance();

                            hoursCharged.add(cal.get(Calendar.HOUR_OF_DAY));
                        }
                    }
                    Collections.sort(hoursCharged);

                    Integer minTime = hoursCharged.get(0);
                    for (int i = minTime; i <= 0; i--) {
                        dictionaryBattery.get(key).get(key1).get(key2);
                    }
                }
            }
        }
    }

    public TimeUtil getTimeUtil() {
        return timeUtil;
    }

    public void setTimeUtil(TimeUtil timeUtil) {
        this.timeUtil = timeUtil;
    }
}
