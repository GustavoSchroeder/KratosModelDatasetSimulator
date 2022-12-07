/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model.generator;

import br.unisinos.pojo.ContextInformation.ApplicationUse;
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

/**
 *
 * @author gustavolazarottoschroeder
 */
public class ContextGenerator {

    private ApplicationUseGenerator applicationUseGenerator;
    private DeviceInformationGenerator deviceInformationGenerator;
    private PersonUtil personUtil;
    private TimeUtil timeUtil;

    public ContextGenerator() {
        this.applicationUseGenerator = new ApplicationUseGenerator();
        this.deviceInformationGenerator = new DeviceInformationGenerator();
        this.personUtil = new PersonUtil();
        this.timeUtil = new TimeUtil();
    }

    public void generateContext() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        List<Person> persons = this.personUtil.fetchPersons();

        Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneLock>>>>> dictionaryScreenStatus
                = this.deviceInformationGenerator.organizeScreenStatus(persons);

        Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>>> dictionaryPowerEvents
                = this.deviceInformationGenerator.organizePowerEvents(persons);

        printHeader();

        for (Person person : persons) {
            Calendar cal = Calendar.getInstance();

            cal.add(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            String lastDayTypeControl = "";
            Map<String, List<Long>> dictionaryDatesAppIds = new HashMap<>();
            for (int j = 0; j < 30; j++) {
                String dayType = this.timeUtil.checkWeekDay(cal.getTime());
                if (!lastDayTypeControl.equalsIgnoreCase(dayType + ";" + person.getId())) {
                    dictionaryDatesAppIds = this.applicationUseGenerator.fetchApplicationsIds(dayType, person.getId());
                    lastDayTypeControl = dayType + ";" + person.getId();
                }

                Map<Integer, List<ApplicationUse>> appInUse = this.applicationUseGenerator.randomDayApplicationDay(dictionaryDatesAppIds);

                for (int i = 0; i < 24; i++) {
                    List<ApplicationUse> applications = appInUse.get(i);
                    if (null == applications) {
                        applications = new ArrayList<>();
                    }

                    Map<Integer, List<String>> mapScreenStatus
                            = this.applicationUseGenerator.analyseScreenStatus(new ArrayList<>(applications));

                    String appHighUseTime = "";
                    Long applicationUseTime = 0L;

                    String applicationCategoryTopInUse = "";
                    Long categoryUseTime = 0L;

                    Integer minutesLocked = this.applicationUseGenerator.minutesLocked(mapScreenStatus);
                    Integer minutesUnlocked = this.applicationUseGenerator.minutesUnlocked(mapScreenStatus);

                    Map<String, Long> categoryMinutes = new HashMap<>();
                    Map<String, Long> applicationMinutes = new HashMap<>();
                    if (null != applications && !applications.isEmpty()) {
                        // category time spent
                        categoryMinutes = this.applicationUseGenerator.calculateCategoryTimeSpent(new ArrayList<>(applications));
                        Object[] categoryTopSpent = this.applicationUseGenerator.calculateTopTimeSpent(categoryMinutes);
                        applicationCategoryTopInUse = (String) categoryTopSpent[0];
                        try {
                            categoryUseTime = (((Long) categoryTopSpent[1]) / 60);
                        } catch (Exception e) {
                        }

                        // application time spent
                        applicationMinutes = this.applicationUseGenerator.calculateApplicationTimeSpent(new ArrayList<>(applications));
                        Object[] applicationTopSpent = this.applicationUseGenerator.calculateTopTimeSpent(applicationMinutes);
                        appHighUseTime = (String) applicationTopSpent[0];
                        try {
                            applicationUseTime = (((Long) applicationTopSpent[1]) / 60);
                        } catch (Exception e) {
                        }
                    }

                    Object[] arrayObj = {
                        /*0*/person.getId(),
                        /*1*/ sdf.format(cal.getTime()),
                        /*2*/ dayType,
                        /*3*/ minutesLocked,
                        /*4*/ minutesUnlocked,
                        /*5*/ (minutesLocked == 61 ? "Not Used" : "Used"),
                        /*6*/ applicationCategoryTopInUse,
                        /*7*/ categoryUseTime,
                        /*8*/ appHighUseTime,
                        /*9*/ applicationUseTime
                    };

                    printSb(arrayObj);

                    cal.add(Calendar.HOUR_OF_DAY, 1);
                }
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
    }

    private void printHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("person;");
        sb.append("datetime;");
        sb.append("daytype;");
        sb.append("minuteslocked;");
        sb.append("minutesunlocked;");
        System.out.println(sb);
    }

    private void printSb(Object[] payload) {
        StringBuilder sb = new StringBuilder();
        sb = new StringBuilder();
        sb.append((Long) payload[0]);
        sb.append(";");
        sb.append((String) payload[1]); //sdf.format(cal.getTime())
        sb.append(";");
        sb.append((String) payload[2]); //dayType
        sb.append(";");
        sb.append((Integer) payload[3]); //minutesLocked
        sb.append(";");
        sb.append((Integer) payload[4]); //minutesUnlocked
        sb.append(";");
        sb.append((String) payload[5]);
        sb.append(";");
        sb.append((String) payload[6]);
        sb.append(";");
        sb.append((Long) payload[7]);
        sb.append(";");
        sb.append((String) payload[8]);
        sb.append(";");
        sb.append((Long) payload[9]);
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
}
