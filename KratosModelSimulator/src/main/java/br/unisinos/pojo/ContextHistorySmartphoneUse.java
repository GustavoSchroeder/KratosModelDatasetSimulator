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
import javax.persistence.Transient;

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
    private Integer applicationUseTime;

    //Device Information
    private Integer minutesUnlocked;
    private Integer minutesLocked;
    private String deviceStatus;
    private Double batteryLevel;
    private String powerEvent;
    private String screenStatus;

    //Notification
    private Integer quantityNotifications;
    private String categoryMaxNotifications;
    private Integer categoryNotificationsNumb;

    //Mood
    private Integer sleepHoursEMA;
    private Integer sleepRateEMA;
    private Integer stressLevelEMA;
    private Integer moodEMA;

    //General Context
    private String ambientLight;
    private Date dateTime;
    private String dayShift;
    private String dayType;
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
    
    private String knnPrediction;
    private Double knnPredictionRating;
    private Double knnDistance;
    
    private Double distanceAddicted;
    private Double distanceNormal;
    
    private String suggestedIntervention;
    
    private String ageCategory;

    public ContextHistorySmartphoneUse() {
    }

    public ContextHistorySmartphoneUse(Person person, 
            Integer appMostUsedTimeInUse, String appCategoryTopUse, 
            String applicationTopUse, Integer applicationUseTime, 
            Integer minutesUnlocked, Integer minutesLocked, 
            String deviceStatus, Double batteryLevel, String powerEvent, 
            String screenStatus, Integer quantityNotifications, 
            String categoryMaxNotifications, Integer categoryNotificationsNumb, 
            Integer sleepHoursEMA, Integer sleepRateEMA, Integer stressLevelEMA,
            Integer moodEMA, String ambientLight, Date dateTime, String dayShift, 
            String dayType, String place, Integer totalResultSAS,
            Boolean smartphoneAddicted, Integer totalResultNomophobia,
            String nomophobiaLevel, Integer stressScore, Integer anxietyScore, 
            Integer depressionScore, String stressStatus, String anxietyStatus, 
            String depressionStatus) {
        this.person = person;
        this.appMostUsedTimeInUse = appMostUsedTimeInUse;
        this.appCategoryTopUse = appCategoryTopUse;
        this.applicationTopUse = applicationTopUse;
        this.applicationUseTime = applicationUseTime;
        this.minutesUnlocked = minutesUnlocked;
        this.minutesLocked = minutesLocked;
        this.deviceStatus = deviceStatus;
        this.batteryLevel = batteryLevel;
        this.powerEvent = powerEvent;
        this.screenStatus = screenStatus;
        this.quantityNotifications = quantityNotifications;
        this.categoryMaxNotifications = categoryMaxNotifications;
        this.categoryNotificationsNumb = categoryNotificationsNumb;
        this.sleepHoursEMA = sleepHoursEMA;
        this.sleepRateEMA = sleepRateEMA;
        this.stressLevelEMA = stressLevelEMA;
        this.moodEMA = moodEMA;
        this.ambientLight = ambientLight;
        this.dateTime = dateTime;
        this.dayShift = dayShift;
        this.dayType = dayType;
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

    public Integer getApplicationUseTime() {
        return applicationUseTime;
    }

    public void setApplicationUseTime(Integer applicationUseTime) {
        this.applicationUseTime = applicationUseTime;
    }

    public Integer getMinutesUnlocked() {
        return minutesUnlocked;
    }

    public void setMinutesUnlocked(Integer minutesUnlocked) {
        this.minutesUnlocked = minutesUnlocked;
    }

    public Integer getMinutesLocked() {
        return minutesLocked;
    }

    public void setMinutesLocked(Integer minutesLocked) {
        this.minutesLocked = minutesLocked;
    }

    @Transient
    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
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

    @Transient
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

    public String getCategoryMaxNotifications() {
        return categoryMaxNotifications;
    }

    public void setCategoryMaxNotifications(String categoryMaxNotifications) {
        this.categoryMaxNotifications = categoryMaxNotifications;
    }

    public Integer getCategoryNotificationsNumb() {
        return categoryNotificationsNumb;
    }

    public void setCategoryNotificationsNumb(Integer categoryNotificationsNumb) {
        this.categoryNotificationsNumb = categoryNotificationsNumb;
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

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    @Transient
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

    public String getKnnPrediction() {
        return knnPrediction;
    }

    public void setKnnPrediction(String knnPrediction) {
        this.knnPrediction = knnPrediction;
    }

    public Double getKnnPredictionRating() {
        return knnPredictionRating;
    }

    public void setKnnPredictionRating(Double knnPredictionRating) {
        this.knnPredictionRating = knnPredictionRating;
    }

    public Double getKnnDistance() {
        return knnDistance;
    }

    public void setKnnDistance(Double knnDistance) {
        this.knnDistance = knnDistance;
    }

    public Double getDistanceAddicted() {
        return distanceAddicted;
    }

    public void setDistanceAddicted(Double distanceAddicted) {
        this.distanceAddicted = distanceAddicted;
    }

    public Double getDistanceNormal() {
        return distanceNormal;
    }

    public void setDistanceNormal(Double distanceNormal) {
        this.distanceNormal = distanceNormal;
    }

    public String getSuggestedIntervention() {
        return suggestedIntervention;
    }

    public void setSuggestedIntervention(String suggestedIntervention) {
        this.suggestedIntervention = suggestedIntervention;
    }

    public String getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(String ageCategory) {
        this.ageCategory = ageCategory;
    }
}
