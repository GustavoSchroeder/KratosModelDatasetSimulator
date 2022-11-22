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
 */

@Entity
public class MoodEMA implements Serializable{
    private Long id;
    private Person person;
    private Integer happy;
    private Integer happyOrNot;
    private Integer sad;
    private Integer sadOrNot;
    private String location;
    private Date responseTime;

    public MoodEMA() {
    }

    public MoodEMA(Person person, Integer happy, 
            Integer happyOrNot, Integer sad, Integer sadOrNot, 
            String location, Date responseTime) {
        this.person = person;
        this.happy = happy;
        this.happyOrNot = happyOrNot;
        this.sad = sad;
        this.sadOrNot = sadOrNot;
        this.location = location;
        this.responseTime = responseTime;
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

    public Integer getHappy() {
        return happy;
    }

    public void setHappy(Integer happy) {
        this.happy = happy;
    }

    public Integer getHappyOrNot() {
        return happyOrNot;
    }

    public void setHappyOrNot(Integer happyOrNot) {
        this.happyOrNot = happyOrNot;
    }

    public Integer getSad() {
        return sad;
    }

    public void setSad(Integer sad) {
        this.sad = sad;
    }

    public Integer getSadOrNot() {
        return sadOrNot;
    }

    public void setSadOrNot(Integer sadOrNot) {
        this.sadOrNot = sadOrNot;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

}
