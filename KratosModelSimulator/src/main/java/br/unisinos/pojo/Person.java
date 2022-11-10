/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.pojo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author gustavolazarottoschroeder
 */
@Entity
public class Person implements Serializable {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String gender;
    private Integer educationalLevel;

    public Person() {
    }

    public Person(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Person(Long id, String name, String email, Integer age, 
            String gender, Integer educationalLevel) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.educationalLevel = educationalLevel;
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getEducationalLevel() {
        return educationalLevel;
    }

    public void setEducationalLevel(Integer educationalLevel) {
        this.educationalLevel = educationalLevel;
    }

}
