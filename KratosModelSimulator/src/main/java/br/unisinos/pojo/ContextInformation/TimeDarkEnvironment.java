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
 */
@Entity
public class TimeDarkEnvironment implements Serializable {

    private Long id;
    private Person person;
    private Date startTime;
    private Date endTime;
    private Date hoursSpent;

    public TimeDarkEnvironment() {
    }

    public TimeDarkEnvironment(Person person, Date startTime, Date endTime, Date hoursSpent) {
        this.person = person;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hoursSpent = hoursSpent;
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

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
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

}
