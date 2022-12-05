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

        Map<Long, Map<String, Map<Integer, Map<Integer, List<ApplicationUse>>>>> dictionaryApps
                = this.applicationUseGenerator.fetchApplications(persons);

        printHeader();
        
        for (Person person : persons) {
            Calendar cal = Calendar.getInstance();

            cal.add(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            String dayType = this.timeUtil.checkWeekDay(cal.getTime());
            Map<Integer, List<ApplicationUse>> appInUse
                    = this.applicationUseGenerator.randomDayApplicationDay(dictionaryApps.get(person.getId()).get(dayType));
            //List<Integer[]> hoursLocked = this.deviceInformationGenerator.randomDayPhoneLock(dictionaryScreenStatus.get(person.getId()).get(dayType));
            for (int i = 0; i < 24; i++) {
                List<ApplicationUse> applications = appInUse.get(i);
                Map<Integer, List<String>> mapScreenStatus = this.applicationUseGenerator.analyseStringStatus(applications);

                String appHighUseTime = "";
                String applicationCategoryTopInUse = "";
                String applicationTopTimeSpent = "";

                Integer minutesLocked = this.applicationUseGenerator.minutesLocked(mapScreenStatus);
                Integer minutesUnlocked = this.applicationUseGenerator.minutesUnlocked(mapScreenStatus);

                Map<String, Long> categoryMinutes = new HashMap<>();
                if (null != applications) {
                    categoryMinutes = this.applicationUseGenerator.calculateCategoryTimeSpent(applications);

                }

                Object[] arrayObj = {person.getId(), sdf.format(cal.getTime()),
                dayType, minutesLocked, minutesUnlocked};

                printSb(arrayObj);
                
                cal.add(Calendar.HOUR_OF_DAY, 1);
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
