/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.pojo.ContextInformation;

import br.unisinos.pojo.Person;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author gustavolazarottoschroeder
 * levels of higher education students in Ghana and academic achievement
 * https://figshare.com/articles/dataset/Descriptive_statistics_of_students_scores_with_the_NMP-Q_scale_i_N_i_670_/14793089/1
 * https://figshare.com/articles/dataset/NMPQ_data_sav/21507951
 */

@Entity
public class ReportedTimeSpentSmartphone implements Serializable {
    private Long id;
    private Person person;
    private Double hours;

    public ReportedTimeSpentSmartphone() {
    }

    public ReportedTimeSpentSmartphone(Long id, Person person, Double hours) {
        this.id = id;
        this.person = person;
        this.hours = hours;
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

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }
   
}
