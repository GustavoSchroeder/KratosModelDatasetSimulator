/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.pojo;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class TimeDarkEnvironment {

    private Long id;
    private Person person;
    private Date start;
    private Date end;
    private Date hoursSpent;

    public TimeDarkEnvironment() {
    }

    public TimeDarkEnvironment(Person person, Date start, Date end, Date hoursSpent) {
        this.person = person;
        this.start = start;
        this.end = end;
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

    @Temporal(javax.persistence.TemporalType.TIME)
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @Temporal(javax.persistence.TemporalType.TIME)
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Temporal(javax.persistence.TemporalType.TIME)
    public Date getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(Date hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

}
