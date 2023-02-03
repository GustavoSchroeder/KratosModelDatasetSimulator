package br.unisinos.pojo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author gustavolazarottoschroeder
 */
@Entity
public class PersonaSmartphoneAddiction implements Serializable {

    private String id;
    private String typeUser;
    private String gender;
    private String ageCategory;
    private Integer educationLevel;
    private String dayShift;
    private String dayType;

    private Double appMostUsedTimeInUse;
    private Double applicationUseTime;
    private Double minutesUnlocked;
    private Double minutesLocked;
    private Double batteryLevel;
    //powerEvent
    private Double quantityNotifications;
    private Double categoryNotificationsNumb;

    //mood
    private Double sleepHoursEMA;
    private Double sleepRateEMA;
    private Double stressLevelEMA;
    private Double moodEMA;

    //DASS21
    private Double stressScore;
    private Double anxietyScore;
    private Double depressionScore;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(String ageCategory) {
        this.ageCategory = ageCategory;
    }

    public Integer getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(Integer educationLevel) {
        this.educationLevel = educationLevel;
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

    public Double getAppMostUsedTimeInUse() {
        return appMostUsedTimeInUse;
    }

    public void setAppMostUsedTimeInUse(Double appMostUsedTimeInUse) {
        this.appMostUsedTimeInUse = appMostUsedTimeInUse;
    }

    public Double getApplicationUseTime() {
        return applicationUseTime;
    }

    public void setApplicationUseTime(Double applicationUseTime) {
        this.applicationUseTime = applicationUseTime;
    }

    public Double getMinutesUnlocked() {
        return minutesUnlocked;
    }

    public void setMinutesUnlocked(Double minutesUnlocked) {
        this.minutesUnlocked = minutesUnlocked;
    }

    public Double getMinutesLocked() {
        return minutesLocked;
    }

    public void setMinutesLocked(Double minutesLocked) {
        this.minutesLocked = minutesLocked;
    }

    public Double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Double getQuantityNotifications() {
        return quantityNotifications;
    }

    public void setQuantityNotifications(Double quantityNotifications) {
        this.quantityNotifications = quantityNotifications;
    }

    public Double getCategoryNotificationsNumb() {
        return categoryNotificationsNumb;
    }

    public void setCategoryNotificationsNumb(Double categoryNotificationsNumb) {
        this.categoryNotificationsNumb = categoryNotificationsNumb;
    }

    public Double getSleepHoursEMA() {
        return sleepHoursEMA;
    }

    public void setSleepHoursEMA(Double sleepHoursEMA) {
        this.sleepHoursEMA = sleepHoursEMA;
    }

    public Double getSleepRateEMA() {
        return sleepRateEMA;
    }

    public void setSleepRateEMA(Double sleepRateEMA) {
        this.sleepRateEMA = sleepRateEMA;
    }

    public Double getStressLevelEMA() {
        return stressLevelEMA;
    }

    public void setStressLevelEMA(Double stressLevelEMA) {
        this.stressLevelEMA = stressLevelEMA;
    }

    public Double getMoodEMA() {
        return moodEMA;
    }

    public void setMoodEMA(Double moodEMA) {
        this.moodEMA = moodEMA;
    }

    public Double getStressScore() {
        return stressScore;
    }

    public void setStressScore(Double stressScore) {
        this.stressScore = stressScore;
    }

    public Double getAnxietyScore() {
        return anxietyScore;
    }

    public void setAnxietyScore(Double anxietyScore) {
        this.anxietyScore = anxietyScore;
    }

    public Double getDepressionScore() {
        return depressionScore;
    }

    public void setDepressionScore(Double depressionScore) {
        this.depressionScore = depressionScore;
    }
}
