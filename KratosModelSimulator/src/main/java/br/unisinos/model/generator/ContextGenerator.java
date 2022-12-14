/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model.generator;

import br.unisinos.pojo.ContextInformation.ApplicationUse;
import br.unisinos.pojo.ContextInformation.Notification;
import br.unisinos.pojo.ContextInformation.PhoneCharge;
import br.unisinos.pojo.ContextInformation.PhoneLock;
import br.unisinos.pojo.Person;
import br.unisinos.util.PersonUtil;
import br.unisinos.util.TimeUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class ContextGenerator {

    private ApplicationUseGenerator applicationUseGenerator;
    private DeviceInformationGenerator deviceInformationGenerator;
    private PowerEventGeneretor powerEventGeneretor;
    private NotificationContextGenerator notificationContextGenerator;
    private AmbientLightGenerator ambientLightGenerator;
    private PersonUtil personUtil;
    private TimeUtil timeUtil;

    public ContextGenerator() {
        this.applicationUseGenerator = new ApplicationUseGenerator();
        this.personUtil = new PersonUtil();
        this.deviceInformationGenerator = new DeviceInformationGenerator();
        this.powerEventGeneretor = new PowerEventGeneretor();
        this.notificationContextGenerator = new NotificationContextGenerator();
        this.ambientLightGenerator = new AmbientLightGenerator();
        this.timeUtil = new TimeUtil();
    }

    public void generateContext() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        List<Person> persons = this.personUtil.fetchPersons();

        printHeader();

        for (Person person : persons) {
            Calendar cal = Calendar.getInstance();

            cal.add(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            Map<String, Map<String, Map<Integer, List<Notification>>>> mapNotification
                    = this.notificationContextGenerator.generatePowerEventInformation(person.getId());

            Map<String, Map<String, Map<Integer, String>>> mapPhoneCharge
                    = this.powerEventGeneretor.generatePowerEventInformation(person.getId());

            Map<String, Map<String, Map<Integer, String>>> mapAmbientLight
                    = this.ambientLightGenerator.generateLightInfo(person.getId());

            String lastDayTypeControl = "";
            Map<String, List<Long>> dictionaryDatesAppIds = new HashMap<>();

            Double batteryLevel = ((Integer) (new Random().nextInt(70) + 30)).doubleValue();
            Double batteryLevelControl = batteryLevel;
            for (int j = 0; j < 30; j++) {
                String dayType = this.timeUtil.checkWeekDay(cal.getTime());
                if (!lastDayTypeControl.equalsIgnoreCase(dayType + ";" + person.getId())) {
                    dictionaryDatesAppIds = this.applicationUseGenerator.fetchApplicationsIds(dayType, person.getId());
                    lastDayTypeControl = dayType + ";" + person.getId();
                }

                Map<Integer, String> mapChargeBehavior = this.powerEventGeneretor.fetchRandomDayChargingBehavior(dayType, mapPhoneCharge);

                Map<Integer, List<ApplicationUse>> appInUse = this.applicationUseGenerator.randomDayApplicationDay(dictionaryDatesAppIds);

                Map<Integer, String> ambientLightDictionary = this.ambientLightGenerator.fetchRandomDayAmbientLight(dayType, mapAmbientLight);

                for (int i = 0; i < 24; i++) {
                    List<ApplicationUse> applications = appInUse.get(i);
                    if (null == applications) {
                        applications = new ArrayList<>();
                    }

                    Map<Integer, List<String>> mapScreenStatus
                            = this.applicationUseGenerator.analyseScreenStatus(new ArrayList<>(applications));

                    //DeviceContext
                    String appHighUseTime = "";
                    Long applicationUseTime = 0L;
                    String applicationCategoryTopInUse = "";
                    Long categoryUseTime = 0L;

                    //Notification
                    Object[] notificationInfo = this.notificationContextGenerator.buildNotificationInfo(dayType, i, mapNotification);
                    Integer notificationQuantity = (Integer) notificationInfo[0];
                    String categoryMaxNotifications = (String) notificationInfo[1];
                    Integer categoryNotificationsNumb = (Integer) notificationInfo[2];

                    String powerEvent = mapChargeBehavior.get(i);
                    batteryLevel = this.powerEventGeneretor.generateBatteryLevel(batteryLevel, powerEvent);
                    //String useTime;
                    Integer minutesLocked = 0;
                    Integer minutesUnlocked = 0;

                    if ((batteryLevel == 0 && batteryLevelControl != 0) || batteryLevel > 0) {
                        if (null != applications && !applications.isEmpty()) {
                            Object[] infoScreen = this.applicationUseGenerator.calculateScreeStatus(new ArrayList<>(applications));
                            appHighUseTime = (String) infoScreen[0];
                            applicationUseTime = (Long) infoScreen[1];
                            applicationCategoryTopInUse = (String) infoScreen[2];
                            categoryUseTime = (Long) infoScreen[3];
                            minutesLocked = this.applicationUseGenerator.minutesLocked(mapScreenStatus);
                            minutesUnlocked = this.applicationUseGenerator.minutesUnlocked(mapScreenStatus);
                        }
                    } else {
                        appHighUseTime = "";
                        applicationUseTime = 0L;
                        applicationCategoryTopInUse = "";
                        categoryUseTime = 0L;
                        minutesLocked = 60;
                        minutesUnlocked = 0;
                    }

                    //General Context
                    String ambientLight = ambientLightDictionary.get(i);

                    Object[] arrayObj = {
                        /*0*/ person.getId(),
                        /*1*/ sdf.format(cal.getTime()), //DateTime
                        /*2*/ defineDayShift(cal),
                        /*3*/ dayType,
                        /*4*/ minutesLocked, //UseTime
                        /*5*/ minutesUnlocked,
                        /*6*/ (minutesLocked == 61 ? "Not Used" : "Used"),
                        /*7*/ applicationCategoryTopInUse, //ApplicationCategoryTopInUse
                        /*8*/ categoryUseTime, //ApplicationTopTimeSpent
                        /*9*/ appHighUseTime, //AppHighUseTime
                        /*10*/ applicationUseTime, //AppHighTimeSpent
                        /*11*/ powerEvent, //PowerEvent
                        /*12*/ batteryLevel, //BatteryLevel
                        /*13*/ notificationQuantity, //Notification
                        /*14*/ categoryMaxNotifications, //Notification
                        /*15*/ categoryNotificationsNumb, //Notification
                        /*16*/ ambientLight
                    };

                    printSb(arrayObj);

                    cal.add(Calendar.HOUR_OF_DAY, 1);
                    batteryLevelControl = batteryLevel.doubleValue();
                }
            }
        }
    }

    private String defineDayShift(Calendar cal) {
        Integer hour = cal.get(Calendar.HOUR_OF_DAY);

        if (hour >= 6 && hour < 12) {
            return "Morning";
        } else if (hour >= 12 && hour < 17) {
            return "Afternoon";
        } else if (hour >= 17 && hour < 20) {
            return "Evening";
        } else {
            return "Night";
        }
    }

    private void printHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("person;");
        sb.append("DayShift;");
        sb.append("datetime;");
        sb.append("daytype;");
        sb.append("minutesUnlocked;");
        sb.append("minuteslocked;");
        sb.append("applicationCategoryTopInUse;");
        sb.append("categoryUseTime;");
        sb.append("appHighUseTime;");
        sb.append("applicationUseTime;");
        sb.append("batteryLevel;");
        sb.append("notificationQuantity;");
        sb.append("categoryMaxNotifications;");
        sb.append("categoryNotificationsNumb;");
        sb.append("ambientLight;");
        System.out.println(sb);
    }

    private void printSb(Object[] payload) {
        StringBuilder sb = new StringBuilder();
        sb = new StringBuilder();
        Integer index = 0;
        sb.append((Long) payload[index++]);
        sb.append(";");
        sb.append((String) payload[index++]); //sdf.format(cal.getTime())
        sb.append(";");
        sb.append((String) payload[index++]); //sdf.format(cal.getTime())
        sb.append(";");
        sb.append((String) payload[index++]); //dayType
        sb.append(";");
        sb.append((Integer) payload[index++]); //minutesLocked
        sb.append(";");
        sb.append((Integer) payload[index++]); //minutesUnlocked
        sb.append(";");
        sb.append((String) payload[index++]);
        sb.append(";");
        sb.append((String) payload[index++]);
        sb.append(";");
        sb.append((Long) payload[index++]);
        sb.append(";");
        sb.append((String) payload[index++]);
        sb.append(";");
        sb.append((Long) payload[index++]);
        sb.append(";");
        sb.append((String) payload[index++]);
        sb.append(";");
        sb.append((Double) payload[index++]);
        sb.append(";");
        sb.append((Integer) payload[index++]);
        sb.append(";");
        sb.append((String) payload[index++]);
        sb.append(";");
        sb.append((Integer) payload[index++]);
        sb.append(";");
        sb.append((String) payload[index++]);
        System.out.println(sb);
    }

    public ApplicationUseGenerator getApplicationUseGenerator() {
        return applicationUseGenerator;
    }

    public void setApplicationUseGenerator(ApplicationUseGenerator applicationUseGenerator) {
        this.applicationUseGenerator = applicationUseGenerator;
    }

    public PersonUtil getPersonUtil() {
        return personUtil;
    }

    public void setPersonUtil(PersonUtil personUtil) {
        this.personUtil = personUtil;
    }

    public DeviceInformationGenerator getDeviceInformationGenerator() {
        return deviceInformationGenerator;
    }

    public void setDeviceInformationGenerator(DeviceInformationGenerator deviceInformationGenerator) {
        this.deviceInformationGenerator = deviceInformationGenerator;
    }

    public TimeUtil getTimeUtil() {
        return timeUtil;
    }

    public void setTimeUtil(TimeUtil timeUtil) {
        this.timeUtil = timeUtil;
    }

    public PowerEventGeneretor getPowerEventGeneretor() {
        return powerEventGeneretor;
    }

    public void setPowerEventGeneretor(PowerEventGeneretor powerEventGeneretor) {
        this.powerEventGeneretor = powerEventGeneretor;
    }

    public NotificationContextGenerator getNotificationContextGenerator() {
        return notificationContextGenerator;
    }

    public void setNotificationContextGenerator(NotificationContextGenerator notificationContextGenerator) {
        this.notificationContextGenerator = notificationContextGenerator;
    }

    public AmbientLightGenerator getAmbientLightGenerator() {
        return ambientLightGenerator;
    }

    public void setAmbientLightGenerator(AmbientLightGenerator ambientLightGenerator) {
        this.ambientLightGenerator = ambientLightGenerator;
    }
}
