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
 * @author gustavolazarottoschroeder Studentlife Dataset
 * https://www.kaggle.com/datasets/dartweichen/student-life
 */
@Entity
public class TimeDarkEnvironment implements Serializable {

    private Long id;
    private Person person;
    private Date startTime;
    private Date endTime;
    private Date hoursSpent;
    private Date startDate;
    private Date endDate;

    public TimeDarkEnvironment() {
    }

    public TimeDarkEnvironment(
            Person person,
            Date startTime,
            Date endTime,
            Date hoursSpent,
            Date startDate,
            Date endDate) {
        this.person = person;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hoursSpent = hoursSpent;
        this.startDate = startDate;
        this.endDate = endDate;
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

    @Temporal(javax.persistence.TemporalType.TIME)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(javax.persistence.TemporalType.TIME)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Temporal(javax.persistence.TemporalType.TIME)
    public Date getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(Date hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
