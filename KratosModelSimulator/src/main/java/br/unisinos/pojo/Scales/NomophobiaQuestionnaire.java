/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.pojo.Scales;

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
 */
@Entity
public class NomophobiaQuestionnaire implements Serializable {

    private Long id;
    private Person person;
    private Integer nmpqTotal;
    private Integer response1;
    private Integer response2;
    private Integer response3;
    private Integer response4;
    private Integer response5;
    private Integer response6;
    private Integer response7;
    private Integer response8;
    private Integer response9;
    private Integer response10;
    private Integer response11;
    private Integer response12;
    private Integer response13;
    private Integer response14;
    private Integer response15;
    private Integer response16;
    private Integer response17;
    private Integer response18;
    private Integer response19;
    private Integer response20;
    private String category;

    public NomophobiaQuestionnaire() {
    }

    public NomophobiaQuestionnaire(Person person, Integer nmpqTotal,
            Integer response1, Integer response2, 
            Integer response3, Integer response4, Integer response5,
            Integer response6, Integer response7, Integer response8, 
            Integer response9, Integer response10, Integer response11, 
            Integer response12, Integer response13, Integer response14, 
            Integer response15, Integer response16, Integer response17, 
            Integer response18, Integer response19, Integer response20, 
            String category) {
        this.person = person;
        this.nmpqTotal = nmpqTotal;
        this.response1 = response1;
        this.response2 = response2;
        this.response3 = response3;
        this.response4 = response4;
        this.response5 = response5;
        this.response6 = response6;
        this.response7 = response7;
        this.response8 = response8;
        this.response9 = response9;
        this.response10 = response10;
        this.response11 = response11;
        this.response12 = response12;
        this.response13 = response13;
        this.response14 = response14;
        this.response15 = response15;
        this.response16 = response16;
        this.response17 = response17;
        this.response18 = response18;
        this.response19 = response19;
        this.response20 = response20;
        this.category = category;
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

    public Integer getNmpqTotal() {
        return nmpqTotal;
    }

    public void setNmpqTotal(Integer nmpqTotal) {
        this.nmpqTotal = nmpqTotal;
    }
    
    public Integer getResponse1() {
        return response1;
    }

    public void setResponse1(Integer response1) {
        this.response1 = response1;
    }

    public Integer getResponse2() {
        return response2;
    }

    public void setResponse2(Integer response2) {
        this.response2 = response2;
    }

    public Integer getResponse3() {
        return response3;
    }

    public void setResponse3(Integer response3) {
        this.response3 = response3;
    }

    public Integer getResponse4() {
        return response4;
    }

    public void setResponse4(Integer response4) {
        this.response4 = response4;
    }

    public Integer getResponse5() {
        return response5;
    }

    public void setResponse5(Integer response5) {
        this.response5 = response5;
    }

    public Integer getResponse6() {
        return response6;
    }

    public void setResponse6(Integer response6) {
        this.response6 = response6;
    }

    public Integer getResponse7() {
        return response7;
    }

    public void setResponse7(Integer response7) {
        this.response7 = response7;
    }

    public Integer getResponse8() {
        return response8;
    }

    public void setResponse8(Integer response8) {
        this.response8 = response8;
    }

    public Integer getResponse9() {
        return response9;
    }

    public void setResponse9(Integer response9) {
        this.response9 = response9;
    }

    public Integer getResponse10() {
        return response10;
    }

    public void setResponse10(Integer response10) {
        this.response10 = response10;
    }

    public Integer getResponse11() {
        return response11;
    }

    public void setResponse11(Integer response11) {
        this.response11 = response11;
    }

    public Integer getResponse12() {
        return response12;
    }

    public void setResponse12(Integer response12) {
        this.response12 = response12;
    }

    public Integer getResponse13() {
        return response13;
    }

    public void setResponse13(Integer response13) {
        this.response13 = response13;
    }

    public Integer getResponse14() {
        return response14;
    }

    public void setResponse14(Integer response14) {
        this.response14 = response14;
    }

    public Integer getResponse15() {
        return response15;
    }

    public void setResponse15(Integer response15) {
        this.response15 = response15;
    }

    public Integer getResponse16() {
        return response16;
    }

    public void setResponse16(Integer response16) {
        this.response16 = response16;
    }

    public Integer getResponse17() {
        return response17;
    }

    public void setResponse17(Integer response17) {
        this.response17 = response17;
    }

    public Integer getResponse18() {
        return response18;
    }

    public void setResponse18(Integer response18) {
        this.response18 = response18;
    }

    public Integer getResponse19() {
        return response19;
    }

    public void setResponse19(Integer response19) {
        this.response19 = response19;
    }

    public Integer getResponse20() {
        return response20;
    }

    public void setResponse20(Integer response20) {
        this.response20 = response20;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
