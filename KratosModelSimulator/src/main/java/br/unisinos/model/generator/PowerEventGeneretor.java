package br.unisinos.model.generator;

import br.unisinos.pojo.ContextInformation.PhoneCharge;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.TimeUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class PowerEventGeneretor {

    private TimeUtil timeUtil;

    public PowerEventGeneretor() {
        this.timeUtil = new TimeUtil();
    }

    public Map<String, Map<String, Map<Integer, String>>> generatePowerEventInformation(Long idPerson) {
        List<PhoneCharge> phoneChargeList = fetchPhoneChargeInfo(idPerson);

        //TypeDay -> Day -> Hour -> phoneCharge
        Map<String, Map<String, Map<Integer, String>>> mapPhoneCharge = new HashMap<>();
        for (PhoneCharge phoneCharge : phoneChargeList) {

            Calendar startDate = Calendar.getInstance();
            startDate.setTime(phoneCharge.getStartDate());

            Calendar endDate = Calendar.getInstance();
            endDate.setTime(phoneCharge.getEndDate());

            String start = "";
            String end = "";
            do {
                if (endDate.before(startDate)) {
                    System.out.println("stop");
                    break;
                }

                String dayType = this.timeUtil.checkWeekDay(phoneCharge.getStartDate());

                if (null == mapPhoneCharge.get(dayType)) {
                    mapPhoneCharge.put(dayType, new HashMap<>());
                }

                String day = startDate.get(Calendar.DAY_OF_MONTH) + "/" + startDate.get(Calendar.MONTH);
                if (null == mapPhoneCharge.get(dayType).get(day)) {
                    mapPhoneCharge.get(dayType).put(day, new HashMap<>());
                }

                if (null == mapPhoneCharge.get(dayType).get(day).get(startDate.get(Calendar.HOUR))) {
                    mapPhoneCharge.get(dayType).get(day).put(startDate.get(Calendar.HOUR), "Charged");
                }

                startDate.add(Calendar.HOUR_OF_DAY, 1);

                start = (startDate.get(Calendar.DAY_OF_MONTH) + ""
                        + startDate.get(Calendar.MONTH)
                        + startDate.get(Calendar.YEAR) + startDate.get(Calendar.HOUR)
                        + "");
                end = endDate.get(Calendar.DAY_OF_MONTH) + ""
                        + endDate.get(Calendar.MONTH)
                        + endDate.get(Calendar.YEAR)
                        + endDate.get(Calendar.HOUR) + "";

                System.out.println(start + ";" + end);
            } while (!start.equalsIgnoreCase(end));
        }

        for (Map.Entry<String, Map<String, Map<Integer, String>>> entry : mapPhoneCharge.entrySet()) {
            String key = entry.getKey();
            Map<String, Map<Integer, String>> value = entry.getValue();
            for (Map.Entry<String, Map<Integer, String>> entry1 : value.entrySet()) {
                String key1 = entry1.getKey();
                Map<Integer, String> value1 = entry1.getValue();
                for (int i = 0; i < 24; i++) {
                    if(null == mapPhoneCharge.get(key).get(key1).get(i)){
                        mapPhoneCharge.get(key).get(key1).put(i, "Discharging");
                    }
                }
            }
        }
        
        return mapPhoneCharge;
    }

    private List<PhoneCharge> fetchPhoneChargeInfo(Long idPerson) {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT i FROM PhoneCharge i WHERE i.person.id = :idPerson");
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
