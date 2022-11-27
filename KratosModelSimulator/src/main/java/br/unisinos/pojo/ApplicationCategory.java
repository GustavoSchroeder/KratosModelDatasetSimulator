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
public class ApplicationCategory implements Serializable {
    private String application;
    private String category;

    public ApplicationCategory() {
    }

    public ApplicationCategory(String application, String category) {
        this.application = application;
        this.category = category;
    }

    @Id
    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
