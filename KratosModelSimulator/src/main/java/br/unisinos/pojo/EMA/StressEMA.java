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
public class StressEMA implements Serializable {

    private Long id;
    private Integer stressLevel;
    private String stressLevelDesc;
    private String location;
    private Date reponseTime;
    private Person person;

    public StressEMA() {
    }

    public StressEMA(Integer stressLevel, String stressLevelDesc, 
            String location, Date reponseTime, Person person) {
        this.stressLevel = stressLevel;
        this.stressLevelDesc = stressLevelDesc;
        this.location = location;
        this.reponseTime = reponseTime;
        this.person = person;
    }

   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(Integer stressLevel) {
        this.stressLevel = stressLevel;
    }

    @OneToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getReponseTime() {
        return reponseTime;
    }

    public void setReponseTime(Date reponseTime) {
        this.reponseTime = reponseTime;
    }

    public String getStressLevelDesc() {
        return stressLevelDesc;
    }

    public void setStressLevelDesc(String stressLevelDesc) {
        this.stressLevelDesc = stressLevelDesc;
    }

}
