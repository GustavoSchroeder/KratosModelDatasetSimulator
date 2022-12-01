/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author gustavolazarottoschroeder
 */
@Entity
public class ContextHistorySmartphoneUse implements Serializable {

    private Long id;
    private Person person;

    //Device Context
    //Application Values
    private Integer appMostUsedTimeInUse;
    private String appCategoryTopUse;
    private String applicationTopUse;
    private Integer meanUseTimeApplications;

    //Device Information
    private Double batteryLevel;
    private String powerEvent;
    private String screenStatus;

    //Notification
    private Integer quantityNotifications;
    private String appTopNotify;
    private Integer notificationIntervals;
    private String categoryTopNotifications;

    //Mood
    private Integer sleepHoursEMA;
    private Integer sleepRateEMA;
    private Integer stressLevelEMA;
    private Integer moodEMA;

    //General Context
    private String ambientLight;
    private Integer timeSpentDark;
    private Date dateTime;
    private String dayShift;
    private String place;

    //Problematic Smartphone Use
    private Integer totalResultSAS;
    private Boolean smartphoneAddicted;

    //Nomophobia
    private Integer totalResultNomophobia;
    private String nomophobiaLevel;

    //DASS21
    private Integer stressScore;
    private Integer anxietyScore;
    private Integer depressionScore;
    private String stressStatus;
    private String anxietyStatus;
    private String depressionStatus;

    public ContextHistorySmartphoneUse() {
    }
    
    public ContextHistorySmartphoneUse(Person person,
            Integer appMostUsedTimeInUse,
            String appCategoryTopUse,
            String applicationTopUse,
            Integer meanUseTimeApplications,
            Double batteryLevel,
            String powerEvent,
            String screenStatus,
            Integer quantityNotifications,
            String appTopNotify,
            Integer notificationIntervals,
            String categoryTopNotifications,
            Integer sleepHoursEMA,
            Integer sleepRateEMA,
            Integer stressLevelEMA,
            Integer moodEMA,
            String ambientLight,
            Integer timeSpentDark,
            Date dateTime,
            String dayShift,
            String place,
            Integer totalResultSAS,
            Boolean smartphoneAddicted,
            Integer totalResultNomophobia,
            String nomophobiaLevel,
            Integer stressScore,
            Integer anxietyScore,
            Integer depressionScore,
            String stressStatus,
            String anxietyStatus,
            String depressionStatus) {
        this.person = person;
        this.appMostUsedTimeInUse = appMostUsedTimeInUse;
        this.appCategoryTopUse = appCategoryTopUse;
        this.applicationTopUse = applicationTopUse;
        this.meanUseTimeApplications = meanUseTimeApplications;
        this.batteryLevel = batteryLevel;
        this.powerEvent = powerEvent;
        this.screenStatus = screenStatus;
        this.quantityNotifications = quantityNotifications;
        this.appTopNotify = appTopNotify;
        this.notificationIntervals = notificationIntervals;
        this.categoryTopNotifications = categoryTopNotifications;
        this.sleepHoursEMA = sleepHoursEMA;
        this.sleepRateEMA = sleepRateEMA;
        this.stressLevelEMA = stressLevelEMA;
        this.moodEMA = moodEMA;
        this.ambientLight = ambientLight;
        this.timeSpentDark = timeSpentDark;
        this.dateTime = dateTime;
        this.dayShift = dayShift;
        this.place = place;
        this.totalResultSAS = totalResultSAS;
        this.smartphoneAddicted = smartphoneAddicted;
        this.totalResultNomophobia = totalResultNomophobia;
        this.nomophobiaLevel = nomophobiaLevel;
        this.stressScore = stressScore;
        this.anxietyScore = anxietyScore;
        this.depressionScore = depressionScore;
        this.stressStatus = stressStatus;
        this.anxietyStatus = anxietyStatus;
        this.depressionStatus = depressionStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Integer getAppMostUsedTimeInUse() {
        return appMostUsedTimeInUse;
    }

    public void setAppMostUsedTimeInUse(Integer appMostUsedTimeInUse) {
        this.appMostUsedTimeInUse = appMostUsedTimeInUse;
    }

    public String getAppCategoryTopUse() {
        return appCategoryTopUse;
    }

    public void setAppCategoryTopUse(String appCategoryTopUse) {
        this.appCategoryTopUse = appCategoryTopUse;
    }

    public String getApplicationTopUse() {
        return applicationTopUse;
    }

    public void setApplicationTopUse(String applicationTopUse) {
        this.applicationTopUse = applicationTopUse;
    }

    public Integer getMeanUseTimeApplications() {
        return meanUseTimeApplications;
    }

    public void setMeanUseTimeApplications(Integer meanUseTimeApplications) {
        this.meanUseTimeApplications = meanUseTimeApplications;
    }

    public Double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Double batteryLevel) {
        this.batteryLevel = batteryLevel;
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

    public Integer getQuantityNotifications() {
        return quantityNotifications;
    }

    public void setQuantityNotifications(Integer quantityNotifications) {
        this.quantityNotifications = quantityNotifications;
    }

    public String getAppTopNotify() {
        return appTopNotify;
    }

    public void setAppTopNotify(String appTopNotify) {
        this.appTopNotify = appTopNotify;
    }

    public Integer getNotificationIntervals() {
        return notificationIntervals;
    }

    public void setNotificationIntervals(Integer notificationIntervals) {
        this.notificationIntervals = notificationIntervals;
    }

    public String getCategoryTopNotifications() {
        return categoryTopNotifications;
    }

    public void setCategoryTopNotifications(String categoryTopNotifications) {
        this.categoryTopNotifications = categoryTopNotifications;
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

    public Integer getStressLevelEMA() {
        return stressLevelEMA;
    }

    public void setStressLevelEMA(Integer stressLevelEMA) {
        this.stressLevelEMA = stressLevelEMA;
    }

    public Integer getMoodEMA() {
        return moodEMA;
    }

    public void setMoodEMA(Integer moodEMA) {
        this.moodEMA = moodEMA;
    }

    public String getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(String ambientLight) {
        this.ambientLight = ambientLight;
    }

    public Integer getTimeSpentDark() {
        return timeSpentDark;
    }

    public void setTimeSpentDark(Integer timeSpentDark) {
        this.timeSpentDark = timeSpentDark;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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

    public Integer getTotalResultSAS() {
        return totalResultSAS;
    }

    public void setTotalResultSAS(Integer totalResultSAS) {
        this.totalResultSAS = totalResultSAS;
    }

    public Boolean getSmartphoneAddicted() {
        return smartphoneAddicted;
    }

    public void setSmartphoneAddicted(Boolean smartphoneAddicted) {
        this.smartphoneAddicted = smartphoneAddicted;
    }

    public Integer getTotalResultNomophobia() {
        return totalResultNomophobia;
    }

    public void setTotalResultNomophobia(Integer totalResultNomophobia) {
        this.totalResultNomophobia = totalResultNomophobia;
    }

    public String getNomophobiaLevel() {
        return nomophobiaLevel;
    }

    public void setNomophobiaLevel(String nomophobiaLevel) {
        this.nomophobiaLevel = nomophobiaLevel;
    }

    public Integer getStressScore() {
        return stressScore;
    }

    public void setStressScore(Integer stressScore) {
        this.stressScore = stressScore;
    }

    public Integer getAnxietyScore() {
        return anxietyScore;
    }

    public void setAnxietyScore(Integer anxietyScore) {
        this.anxietyScore = anxietyScore;
    }

    public Integer getDepressionScore() {
        return depressionScore;
    }

    public void setDepressionScore(Integer depressionScore) {
        this.depressionScore = depressionScore;
    }

    public String getStressStatus() {
        return stressStatus;
    }

    public void setStressStatus(String stressStatus) {
        this.stressStatus = stressStatus;
    }

    public String getAnxietyStatus() {
        return anxietyStatus;
    }

    public void setAnxietyStatus(String anxietyStatus) {
        this.anxietyStatus = anxietyStatus;
    }

    public String getDepressionStatus() {
        return depressionStatus;
    }

    public void setDepressionStatus(String depressionStatus) {
        this.depressionStatus = depressionStatus;
    }
}
