/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.pojo.ContextInformation.Notification;
import br.unisinos.pojo.Person;
import br.unisinos.util.PersonUtil;
import br.unisinos.util.FileUtil;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.TimeUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.EntityManager;

/**
 *
 * @author gustavolazarottoschroeder Import files from
 * https://github.com/aliannejadi/LSApp
 */
public class ImportNotification implements Serializable {

    private final PersonUtil personUtil;
    private final TimeUtil timeUtil;

    public ImportNotification() {
        this.personUtil = new PersonUtil();
        this.timeUtil = new TimeUtil();
    }

    public void importFiles() throws FileNotFoundException, IOException, ParseException {
        String folder = "./src/main/java/files/notification/low_level/notification.csv";

        EntityManager em = JPAUtil.getEntityManager();

        Map<String, String> appsCategory = this.personUtil.fetchAppCategory();

        Map<Long, Person> persons = this.personUtil.findPersonList();
        Map<Long, List<Notification>> mapNotifications = new HashMap<>();

        try {
            try ( BufferedReader br = new BufferedReader(new FileReader(folder))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.replace("\\", "").replace("'", "");
                    String[] values = line.split(",");

                    if (values[1].contains("id")) {
                        continue;
                    }

                    Long userId = Long.parseLong(values[0].trim());
                    Long id = Long.parseLong(values[1].trim());
                    Long nid = Long.parseLong(values[2].trim());
                    Integer priority = Integer.parseInt(values[3].trim());
                    String packageName = values[4].trim();
                    String appCategory = appsCategory.get(packageName);

                    Long milliTimePosted = Long.parseLong(values[5].trim());
                    Date timePosted = this.timeUtil.convertUnixTime(milliTimePosted);

                    Long milliTimeRemoved = Long.parseLong(values[6].trim());
                    Date timeRemoved = this.timeUtil.convertUnixTime(milliTimeRemoved);

                    Integer sound = null;
                    try {
                        sound = Integer.parseInt(values[7].trim());
                    } catch (Exception e) {
                    }

                    Integer defaultSound = null;
                    try {
                        defaultSound = Integer.parseInt(values[8].trim());
                    } catch (Exception e) {
                    }

                    Integer led = null;
                    try {
                        led = Integer.parseInt(values[9].trim());
                    } catch (Exception e) {
                    }

                    Integer defaultLed = null;
                    try {
                        defaultLed = Integer.parseInt(values[10].trim());
                    } catch (Exception e) {
                    }

                    Integer vibrationPattern = null;
                    try {
                        vibrationPattern = Integer.parseInt(values[11].trim());
                    } catch (Exception e) {
                    }

                    Integer defaultVibration = null;
                    try {
                        defaultVibration = Integer.parseInt(values[12].trim());
                    } catch (Exception e) {
                    }

                    Integer ringerMode = null;
                    try {
                        ringerMode = Integer.parseInt(values[13].trim());
                    } catch (Exception e) {
                    }

                    Integer idle = null;
                    try {
                        idle = Integer.parseInt(values[14].trim());
                    } catch (Exception e) {
                    }

                    Integer interactive = null;
                    try {
                        interactive = Integer.parseInt(values[15].trim());
                    } catch (Exception e) {
                    }

                    Integer screenState = null;
                    try {
                        screenState = Integer.parseInt(values[16].trim());
                    } catch (Exception e) {
                    }

                    Integer lockScrNotifs = null;
                    try {
                        lockScrNotifs = Integer.parseInt(values[17].trim());
                    } catch (Exception e) {
                    }

                    Integer flags = null;
                    try {
                        flags = Integer.parseInt(values[18].trim());
                    } catch (Exception e) {
                    }

                    Notification notif = new Notification(userId,
                            nid,
                            priority,
                            packageName,
                            timePosted,
                            timeRemoved,
                            sound,
                            defaultSound,
                            led,
                            defaultLed,
                            vibrationPattern,
                            defaultVibration,
                            ringerMode,
                            idle,
                            interactive,
                            screenState,
                            lockScrNotifs,
                            flags,
                            null,
                            appCategory);

                    if (null == mapNotifications.get(userId)) {
                        mapNotifications.put(userId, new ArrayList<>());
                    }
                    mapNotifications.get(userId).add(notif);
                }
            }

            em.getTransaction().begin();

            List<Long> userIds = new ArrayList<>(persons.keySet());
            List<Integer> idsUsed = new ArrayList<>();
            Integer max = userIds.size();

            Random rand = new Random();

            Long counter = 1L;

            for (Integer i = 0; i < userIds.size(); i++) {

                Integer n = null;
                do {
                    n = rand.nextInt(max);
                } while (idsUsed.contains(n));

                idsUsed.add(n);

                List<Notification> notifications = mapNotifications.get(counter);
                Person p = persons.get(userIds.get(n));

                System.out.println(p.getId() + ";" + counter + ";" + notifications.size());

                for (Notification notification : notifications) {
                    notification.setPerson(p);
                    em.merge(notification);
                }

                if ((counter + 1) > 14) {
                    counter = 1L;
                } else {
                    counter++;
                }
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

        em.close();
    }

}
