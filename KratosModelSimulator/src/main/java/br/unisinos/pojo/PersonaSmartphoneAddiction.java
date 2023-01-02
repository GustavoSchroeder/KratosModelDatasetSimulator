package br.unisinos.pojo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author gustavolazarottoschroeder
 */
@Entity
public class PersonaSmartphoneAddiction implements Serializable {

    private Long id;
    private String typeUser;
    private String gender;
    private String ageCategory;
    private Integer educationLevel;
    private String dayShift;
    private String dayType;

    private Integer appMostUsedTimeInUse;
    private Integer applicationUseTime;
    private Integer minutesUnlocked;
    private Integer minutesLocked;
    private Integer batteryLevel;
    //powerEvent
    private Integer quantityNotifications;
    private Integer categoryNotificationsNumb;

    //mood
    private Integer sleepHoursEMA;
    private Integer sleepRateEMA;
    private Integer stressLevelEMA;
    private Integer moodEMA;

    //DASS21
    private Integer stressScore;
    private Integer anxietyScore;
    private Integer depressionScore;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getAppMostUsedTimeInUse() {
        return appMostUsedTimeInUse;
    }

    public void setAppMostUsedTimeInUse(Integer appMostUsedTimeInUse) {
        this.appMostUsedTimeInUse = appMostUsedTimeInUse;
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

    public Integer getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Integer getQuantityNotifications() {
        return quantityNotifications;
    }

    public void setQuantityNotifications(Integer quantityNotifications) {
        this.quantityNotifications = quantityNotifications;
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
}
