/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model.generator;

import br.unisinos.pojo.ContextInformation.ApplicationUse;
import br.unisinos.pojo.ContextInformation.PhoneCharge;
import br.unisinos.pojo.ContextInformation.PhoneLock;
import br.unisinos.pojo.Person;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.PersonUtil;
import br.unisinos.util.TimeUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
        List<Person> persons = this.personUtil.fetchPersons();

        Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneLock>>>>> dictionaryScreenStatus
                = this.deviceInformationGenerator.organizeScreenStatus(persons);

        Map<Long, Map<String, Map<Integer, Map<Integer, List<PhoneCharge>>>>> dictionaryPowerEvents
                = this.deviceInformationGenerator.organizePowerEvents(persons);

        Map<Long, Map<String, Map<Integer, Map<Integer, List<ApplicationUse>>>>> dictionaryApps
                = this.applicationUseGenerator.fetchApplications(persons);

        for (Person person : persons) {
            Calendar cal = Calendar.getInstance();

            cal.add(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            for (int i = 0; i < 24; i++) {

                String dayType = this.timeUtil.checkWeekDay(cal.getTime());

                String screen = "";

                Map<Integer, List<PhoneLock>> phoneLock = this.deviceInformationGenerator.randomDayPhoneLock(dictionaryScreenStatus.get(person.getId()).get(dayType));
                List<PhoneLock> screenStatus = phoneLock.get(0);
                //Quantity of time
                //Locked or Unlocked
                
                if (screenStatus.isEmpty()) {
                    screen = "Locked";
                } else {

                }

                cal.add(Calendar.HOUR, 1);

            }

        }
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
