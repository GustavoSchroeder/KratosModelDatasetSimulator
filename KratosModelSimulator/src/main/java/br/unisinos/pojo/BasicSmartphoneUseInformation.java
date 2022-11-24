/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
public class BasicSmartphoneUseInformation implements Serializable {
    private Long id;
    private Integer age;
    private Integer ageGroup;
    private Integer gender;
    private Integer income;
    private Double unlocks;
    private Double time;
    private Double checks;
    private Double appsOpen;
    private Double notificationDay;

    public BasicSmartphoneUseInformation() {
    }

    public BasicSmartphoneUseInformation(Integer age, Integer ageGroup, 
            Integer gender, Integer income, Double unlocks, Double time, 
            Double checks, Double appsOpen, Double notificationDay) {
        this.age = age;
        this.ageGroup = ageGroup;
        this.gender = gender;
        this.income = income;
        this.unlocks = unlocks;
        this.time = time;
        this.checks = checks;
        this.appsOpen = appsOpen;
        this.notificationDay = notificationDay;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(Integer ageGroup) {
        this.ageGroup = ageGroup;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Double getUnlocks() {
        return unlocks;
    }

    public void setUnlocks(Double unlocks) {
        this.unlocks = unlocks;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getChecks() {
        return checks;
    }

    public void setChecks(Double checks) {
        this.checks = checks;
    }

    public Double getAppsOpen() {
        return appsOpen;
    }

    public void setAppsOpen(Double appsOpen) {
        this.appsOpen = appsOpen;
    }

    public Double getNotificationDay() {
        return notificationDay;
    }

    public void setNotificationDay(Double notificationDay) {
        this.notificationDay = notificationDay;
    }
    
}
