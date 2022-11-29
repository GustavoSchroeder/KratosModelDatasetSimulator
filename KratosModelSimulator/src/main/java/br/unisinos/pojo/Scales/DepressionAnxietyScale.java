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
 * This data was collected with an on-line version of the Depression Anxiety Stress Scales (DASS), see http://www2.psy.unsw.edu.au/dass/
 * https://www.kaggle.com/datasets/lucasgreenwell/depression-anxiety-stress-scales-responses
 */
@Entity
public class DepressionAnxietyScale implements Serializable {

    private Long id;
    private Person person;
    private Integer question1;
    private Integer question2;
    private Integer question3;
    private Integer question4;
    private Integer question5;
    private Integer question6;
    private Integer question7;
    private Integer question8;
    private Integer question9;
    private Integer question10;
    private Integer question11;
    private Integer question12;
    private Integer question13;
    private Integer question14;
    private Integer question15;
    private Integer question16;
    private Integer question17;
    private Integer question18;
    private Integer question19;
    private Integer question20;
    private Integer question21;
    private Integer stressScore;
    private Integer anxietyScore;
    private Integer depressionScore;
    private String stressStatus;
    private String anxietyStatus;
    private String depressionStatus;
    private Integer age; 
    private Integer gender; //"What is your gender?", 1=Male, 2=Female, 3=Other
    private Integer education; //"How much education have you completed?", 1=Less than high school, 2=High school, 3=University degree, 4=Graduate degree

    public DepressionAnxietyScale() {
    }

    public DepressionAnxietyScale(Person person, Integer question1, 
            Integer question2, Integer question3, Integer question4, 
            Integer question5, Integer question6, Integer question7, 
            Integer question8, Integer question9, Integer question10, 
            Integer question11, Integer question12, Integer question13, 
            Integer question14, Integer question15, Integer question16, 
            Integer question17, Integer question18, Integer question19, 
            Integer question20, Integer question21, Integer stressScore, 
            Integer anxietyScore,
            Integer depressionScore, String stressStatus, 
            String anxietyStatus, String depressionStatus,
            Integer age, Integer gender, Integer education) {
        this.person = person;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.question5 = question5;
        this.question6 = question6;
        this.question7 = question7;
        this.question8 = question8;
        this.question9 = question9;
        this.question10 = question10;
        this.question11 = question11;
        this.question12 = question12;
        this.question13 = question13;
        this.question14 = question14;
        this.question15 = question15;
        this.question16 = question16;
        this.question17 = question17;
        this.question18 = question18;
        this.question19 = question19;
        this.question20 = question20;
        this.question21 = question21;
        this.stressScore = stressScore;
        this.anxietyScore = anxietyScore;
        this.depressionScore = depressionScore;
        this.stressStatus = stressStatus;
        this.anxietyStatus = anxietyStatus;
        this.depressionStatus = depressionStatus;
        this.age = age;
        this.gender = gender;
        this.education = education;
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

    public Integer getQuestion1() {
        return question1;
    }

    public void setQuestion1(Integer question1) {
        this.question1 = question1;
    }

    public Integer getQuestion2() {
        return question2;
    }

    public void setQuestion2(Integer question2) {
        this.question2 = question2;
    }

    public Integer getQuestion3() {
        return question3;
    }

    public void setQuestion3(Integer question3) {
        this.question3 = question3;
    }

    public Integer getQuestion4() {
        return question4;
    }

    public void setQuestion4(Integer question4) {
        this.question4 = question4;
    }

    public Integer getQuestion5() {
        return question5;
    }

    public void setQuestion5(Integer question5) {
        this.question5 = question5;
    }

    public Integer getQuestion6() {
        return question6;
    }

    public void setQuestion6(Integer question6) {
        this.question6 = question6;
    }

    public Integer getQuestion7() {
        return question7;
    }

    public void setQuestion7(Integer question7) {
        this.question7 = question7;
    }

    public Integer getQuestion8() {
        return question8;
    }

    public void setQuestion8(Integer question8) {
        this.question8 = question8;
    }

    public Integer getQuestion9() {
        return question9;
    }

    public void setQuestion9(Integer question9) {
        this.question9 = question9;
    }

    public Integer getQuestion10() {
        return question10;
    }

    public void setQuestion10(Integer question10) {
        this.question10 = question10;
    }

    public Integer getQuestion11() {
        return question11;
    }

    public void setQuestion11(Integer question11) {
        this.question11 = question11;
    }

    public Integer getQuestion12() {
        return question12;
    }

    public void setQuestion12(Integer question12) {
        this.question12 = question12;
    }

    public Integer getQuestion13() {
        return question13;
    }

    public void setQuestion13(Integer question13) {
        this.question13 = question13;
    }

    public Integer getQuestion14() {
        return question14;
    }

    public void setQuestion14(Integer question14) {
        this.question14 = question14;
    }

    public Integer getQuestion15() {
        return question15;
    }

    public void setQuestion15(Integer question15) {
        this.question15 = question15;
    }

    public Integer getQuestion16() {
        return question16;
    }

    public void setQuestion16(Integer question16) {
        this.question16 = question16;
    }

    public Integer getQuestion17() {
        return question17;
    }

    public void setQuestion17(Integer question17) {
        this.question17 = question17;
    }

    public Integer getQuestion18() {
        return question18;
    }

    public void setQuestion18(Integer question18) {
        this.question18 = question18;
    }

    public Integer getQuestion19() {
        return question19;
    }

    public void setQuestion19(Integer question19) {
        this.question19 = question19;
    }

    public Integer getQuestion20() {
        return question20;
    }

    public void setQuestion20(Integer question20) {
        this.question20 = question20;
    }

    public Integer getStressScore() {
        return stressScore;
    }

    public void setStressScore(Integer stressScore) {
        this.stressScore = stressScore;
    }

    public Integer getAnxietyScore() {
        return anxietyScore;
    }

    public void setAnxietyScore(Integer anxietyScore) {
        this.anxietyScore = anxietyScore;
    }

    public Integer getDepressionScore() {
        return depressionScore;
    }

    public void setDepressionScore(Integer depressionScore) {
        this.depressionScore = depressionScore;
    }

    public String getStressStatus() {
        return stressStatus;
    }

    public void setStressStatus(String stressStatus) {
        this.stressStatus = stressStatus;
    }

    public String getAnxietyStatus() {
        return anxietyStatus;
    }

    public void setAnxietyStatus(String anxietyStatus) {
        this.anxietyStatus = anxietyStatus;
    }

    public String getDepressionStatus() {
        return depressionStatus;
    }

    public void setDepressionStatus(String depressionStatus) {
        this.depressionStatus = depressionStatus;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getQuestion21() {
        return question21;
    }

    public void setQuestion21(Integer question21) {
        this.question21 = question21;
    }
    
    
}
