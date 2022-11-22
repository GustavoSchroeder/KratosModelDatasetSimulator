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
    private String happyDesc;
    private String happyOrNotDesc;
    private String sadDesc;
    private String sadOrNotDesc;
    
    public MoodEMA() {
    }

    public MoodEMA(Person person, Integer happy, Integer happyOrNot, 
            Integer sad, Integer sadOrNot, String location,
            Date responseTime, String happyDesc, String happyOrNotDesc, 
            String sadDesc, String sadOrNotDesc) {
        this.person = person;
        this.happy = happy;
        this.happyOrNot = happyOrNot;
        this.sad = sad;
        this.sadOrNot = sadOrNot;
        this.location = location;
        this.responseTime = responseTime;
        this.happyDesc = happyDesc;
        this.happyOrNotDesc = happyOrNotDesc;
        this.sadDesc = sadDesc;
        this.sadOrNotDesc = sadOrNotDesc;
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

    public String getHappyDesc() {
        return happyDesc;
    }

    public void setHappyDesc(String happyDesc) {
        this.happyDesc = happyDesc;
    }

    public String getHappyOrNotDesc() {
        return happyOrNotDesc;
    }

    public void setHappyOrNotDesc(String happyOrNotDesc) {
        this.happyOrNotDesc = happyOrNotDesc;
    }

    public String getSadDesc() {
        return sadDesc;
    }

    public void setSadDesc(String sadDesc) {
        this.sadDesc = sadDesc;
    }

    public String getSadOrNotDesc() {
        return sadOrNotDesc;
    }

    public void setSadOrNotDesc(String sadOrNotDesc) {
        this.sadOrNotDesc = sadOrNotDesc;
    }

}
