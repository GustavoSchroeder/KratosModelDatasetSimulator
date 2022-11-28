/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.pojo.EMA;

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
 * Studentlife Dataset https://www.kaggle.com/datasets/dartweichen/student-life
 */
@Entity
public class SleepEMA implements Serializable {

    private Long id;
    private Integer hours;
    private String location;
    private Integer rate;
    private Date responseTime;
    private Date responseDate;
    private Integer social;
    private Person person;

    public SleepEMA() {
    }

    public SleepEMA(Integer hours, 
            String location, 
            Integer rate, 
            Date responseTime, 
            Integer social, 
            Person person,
            Date responseDate) {
        this.hours = hours;
        this.location = location;
        this.rate = rate;
        this.responseTime = responseTime;
        this.social = social;
        this.person = person;
        this.responseDate = responseDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Temporal(javax.persistence.TemporalType.TIME)
    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }
    
    public Integer getSocial() {
        return social;
    }

    public void setSocial(Integer social) {
        this.social = social;
    }

    @OneToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }
}
