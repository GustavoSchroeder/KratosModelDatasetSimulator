/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author gustavolazarottoschroeder
 */

@Entity
public class HistoricoContexto implements Serializable {

    private Long id;
    private Date dataHora;

    //Device Context
    private String applicationInUse;
    private String applicationCategoryTopInUse;
    private Date applicationTopTimeSpent;
    private Double batteryLevel;
    private Integer quantityNotifications;
    private String powerEvent;
    private String screenStatus;
    private Date useTime;

    //EMA/EMS
    private Integer anxietyEMA;
    private Integer moodEMA;
    private Integer sleepHoursEMA;
    private Integer sleepRateEMA;
    private Integer stressEMA;

    //GeneralContext
    private String ambientLight;
    private String dayShift;
    private String place;

    private Person person;
    
    private List<ScaleAnswer> scales;

    public HistoricoContexto() {
    }

    public HistoricoContexto(Date dataHora, String applicationInUse, 
            String applicationCategoryTopInUse, Date applicationTopTimeSpent, 
            Double batteryLevel, Integer quantityNotifications, String powerEvent, 
            String screenStatus, Date useTime, Integer anxietyEMA, Integer moodEMA, 
            Integer sleepHoursEMA, Integer sleepRateEMA, Integer stressEMA, 
            String ambientLight, String dayShift, String place, Person person, 
            List<ScaleAnswer> scales) {
        this.dataHora = dataHora;
        this.applicationInUse = applicationInUse;
        this.applicationCategoryTopInUse = applicationCategoryTopInUse;
        this.applicationTopTimeSpent = applicationTopTimeSpent;
        this.batteryLevel = batteryLevel;
        this.quantityNotifications = quantityNotifications;
        this.powerEvent = powerEvent;
        this.screenStatus = screenStatus;
        this.useTime = useTime;
        this.anxietyEMA = anxietyEMA;
        this.moodEMA = moodEMA;
        this.sleepHoursEMA = sleepHoursEMA;
        this.sleepRateEMA = sleepRateEMA;
        this.stressEMA = stressEMA;
        this.ambientLight = ambientLight;
        this.dayShift = dayShift;
        this.place = place;
        this.person = person;
        this.scales = scales;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getApplicationInUse() {
        return applicationInUse;
    }

    public void setApplicationInUse(String applicationInUse) {
        this.applicationInUse = applicationInUse;
    }

    public String getApplicationCategoryTopInUse() {
        return applicationCategoryTopInUse;
    }

    public void setApplicationCategoryTopInUse(String applicationCategoryTopInUse) {
        this.applicationCategoryTopInUse = applicationCategoryTopInUse;
    }

    @Temporal(javax.persistence.TemporalType.TIME)
    public Date getApplicationTopTimeSpent() {
        return applicationTopTimeSpent;
    }

    public void setApplicationTopTimeSpent(Date applicationTopTimeSpent) {
        this.applicationTopTimeSpent = applicationTopTimeSpent;
    }

    public Double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Integer getQuantityNotifications() {
        return quantityNotifications;
    }

    public void setQuantityNotifications(Integer quantityNotifications) {
        this.quantityNotifications = quantityNotifications;
    }

    public String getPowerEvent() {
        return powerEvent;
    }

    public void setPowerEvent(String powerEvent) {
        this.powerEvent = powerEvent;
    }

    public String getScreenStatus() {
        return screenStatus;
    }

    public void setScreenStatus(String screenStatus) {
        this.screenStatus = screenStatus;
    }

    @Temporal(javax.persistence.TemporalType.TIME)
    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Integer getAnxietyEMA() {
        return anxietyEMA;
    }

    public void setAnxietyEMA(Integer anxietyEMA) {
        this.anxietyEMA = anxietyEMA;
    }

    public Integer getMoodEMA() {
        return moodEMA;
    }

    public void setMoodEMA(Integer moodEMA) {
        this.moodEMA = moodEMA;
    }

    public Integer getSleepHoursEMA() {
        return sleepHoursEMA;
    }

    public void setSleepHoursEMA(Integer sleepHoursEMA) {
        this.sleepHoursEMA = sleepHoursEMA;
    }

    public Integer getSleepRateEMA() {
        return sleepRateEMA;
    }

    public void setSleepRateEMA(Integer sleepRateEMA) {
        this.sleepRateEMA = sleepRateEMA;
    }

    public Integer getStressEMA() {
        return stressEMA;
    }

    public void setStressEMA(Integer stressEMA) {
        this.stressEMA = stressEMA;
    }

    public String getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(String ambientLight) {
        this.ambientLight = ambientLight;
    }

    public String getDayShift() {
        return dayShift;
    }

    public void setDayShift(String dayShift) {
        this.dayShift = dayShift;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @OneToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @OneToMany
    public List<ScaleAnswer> getScales() {
        return scales;
    }

    public void setScales(List<ScaleAnswer> scales) {
        this.scales = scales;
    }
    
    
   
}
