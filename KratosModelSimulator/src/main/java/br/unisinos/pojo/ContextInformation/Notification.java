/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.pojo.ContextInformation;

import br.unisinos.pojo.Person;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author gustavolazarottoschroeder
 */
@Entity
public class Notification implements Serializable {

    private Long userId;
    private Long id;
    private Long nid;
    private Integer priority;
    private String packageName;
    private Date timePosted;
    private Date timeRemoved;
    private Integer sound;
    private Integer defaultSound;
    private Integer led;
    private Integer defaultLed;
    private Integer vibrationPattern;
    private Integer defaultVibration;
    private Integer ringerMode;
    private Integer idle;
    private Integer interactive;
    private Integer screenState;
    private Integer lockScrNotifs;
    private Integer flags;
    private Person person;

    public Notification() {
    }

    public Notification(Long userId, Long id, Long nid,
            Integer priority, String packageName, Date timePosted,
            Date timeRemoved, Integer sound, Integer defaultSound,
            Integer led, Integer defaultLed, Integer vibrationPattern,
            Integer defaultVibration, Integer ringerMode, Integer idle,
            Integer interactive, Integer screenState, Integer lockScrNotifs,
            Integer flags, Person person) {
        this.userId = userId;
        this.id = id;
        this.nid = nid;
        this.priority = priority;
        this.packageName = packageName;
        this.timePosted = timePosted;
        this.timeRemoved = timeRemoved;
        this.sound = sound;
        this.defaultSound = defaultSound;
        this.led = led;
        this.defaultLed = defaultLed;
        this.vibrationPattern = vibrationPattern;
        this.defaultVibration = defaultVibration;
        this.ringerMode = ringerMode;
        this.idle = idle;
        this.interactive = interactive;
        this.screenState = screenState;
        this.lockScrNotifs = lockScrNotifs;
        this.flags = flags;
        this.person = person;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNid() {
        return nid;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(Date timePosted) {
        this.timePosted = timePosted;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getTimeRemoved() {
        return timeRemoved;
    }

    public void setTimeRemoved(Date timeRemoved) {
        this.timeRemoved = timeRemoved;
    }

    public Integer getSound() {
        return sound;
    }

    public void setSound(Integer sound) {
        this.sound = sound;
    }

    public Integer getDefaultSound() {
        return defaultSound;
    }

    public void setDefaultSound(Integer defaultSound) {
        this.defaultSound = defaultSound;
    }

    public Integer getLed() {
        return led;
    }

    public void setLed(Integer led) {
        this.led = led;
    }

    public Integer getDefaultLed() {
        return defaultLed;
    }

    public void setDefaultLed(Integer defaultLed) {
        this.defaultLed = defaultLed;
    }

    public Integer getVibrationPattern() {
        return vibrationPattern;
    }

    public void setVibrationPattern(Integer vibrationPattern) {
        this.vibrationPattern = vibrationPattern;
    }

    public Integer getDefaultVibration() {
        return defaultVibration;
    }

    public void setDefaultVibration(Integer defaultVibration) {
        this.defaultVibration = defaultVibration;
    }

    public Integer getRingerMode() {
        return ringerMode;
    }

    public void setRingerMode(Integer ringerMode) {
        this.ringerMode = ringerMode;
    }

    public Integer getIdle() {
        return idle;
    }

    public void setIdle(Integer idle) {
        this.idle = idle;
    }

    public Integer getInteractive() {
        return interactive;
    }

    public void setInteractive(Integer interactive) {
        this.interactive = interactive;
    }

    public Integer getScreenState() {
        return screenState;
    }

    public void setScreenState(Integer screenState) {
        this.screenState = screenState;
    }

    public Integer getLockScrNotifs() {
        return lockScrNotifs;
    }

    public void setLockScrNotifs(Integer lockScrNotifs) {
        this.lockScrNotifs = lockScrNotifs;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    @OneToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
