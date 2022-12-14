/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.pojo.ContextInformation;

import br.unisinos.pojo.Person;
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
 * LSApp: Large dataset of Sequential mobile App usage - https://github.com/aliannejadi/LSApp
 */
@Entity
public class ApplicationUse implements Serializable {
    private Long id;
    private Person person;
    private Long sessionId;
    private Date openTime;
    private Date openDate;
    private String appName;
    private String eventType;
    private String appCategory;
    private String dayType;
    private Integer positionLinked;

    public ApplicationUse() {
    }

    public ApplicationUse(Person person, 
            Long sessionId, Date openTime, 
            String appName, String eventType, 
            String appCategory, Date openDate,
            String dayType) {
        this.person = person;
        this.sessionId = sessionId;
        this.openTime = openTime;
        this.appName = appName;
        this.eventType = eventType;
        this.appCategory = appCategory;
        this.openDate = openDate;
        this.dayType = dayType;
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

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    @Temporal(javax.persistence.TemporalType.TIME)
    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getAppCategory() {
        return appCategory;
    }

    public void setAppCategory(String appCategory) {
        this.appCategory = appCategory;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public Integer getPositionLinked() {
        return positionLinked;
    }

    public void setPositionLinked(Integer positionLinked) {
        this.positionLinked = positionLinked;
    }
}
