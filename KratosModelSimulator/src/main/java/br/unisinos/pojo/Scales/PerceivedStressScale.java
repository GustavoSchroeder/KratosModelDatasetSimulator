package br.unisinos.pojo.Scales;

import br.unisinos.pojo.Person;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author gustavo_schroeder
 * Studentlife Dataset https://www.kaggle.com/datasets/dartweichen/student-life
 */
@Entity
public class PerceivedStressScale implements Serializable {

    private String id;
    private Person person;
    private String type;
    private String question1;
    private String question2;
    private String question3;
    private String question4;
    private String question5;
    private String question6;
    private String question7;
    private String question8;
    private String question9;
    private String question10;
    private Integer questionScore1;
    private Integer questionScore2;
    private Integer questionScore3;
    private Integer questionScore4;
    private Integer questionScore5;
    private Integer questionScore6;
    private Integer questionScore7;
    private Integer questionScore8;
    private Integer questionScore9;
    private Integer questionScore10;
    private Integer score;
    private String scoreAlias;

    public PerceivedStressScale() {
    }

    public PerceivedStressScale(String id, Person person, String type, 
            String question1, String question2, String question3, String question4,
            String question5, String question6, String question7, String question8,
            String question9, String question10, Integer questionScore1, 
            Integer questionScore2, Integer questionScore3, Integer questionScore4, 
            Integer questionScore5, Integer questionScore6, Integer questionScore7, 
            Integer questionScore8, Integer questionScore9, Integer questionScore10, 
            Integer score, String scoreAlias) {
        this.id = id;
        this.person = person;
        this.type = type;
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
        this.questionScore1 = questionScore1;
        this.questionScore2 = questionScore2;
        this.questionScore3 = questionScore3;
        this.questionScore4 = questionScore4;
        this.questionScore5 = questionScore5;
        this.questionScore6 = questionScore6;
        this.questionScore7 = questionScore7;
        this.questionScore8 = questionScore8;
        this.questionScore9 = questionScore9;
        this.questionScore10 = questionScore10;
        this.score = score;
        this.scoreAlias = scoreAlias;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @OneToOne
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getQuestion4() {
        return question4;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public String getQuestion5() {
        return question5;
    }

    public void setQuestion5(String question5) {
        this.question5 = question5;
    }

    public String getQuestion6() {
        return question6;
    }

    public void setQuestion6(String question6) {
        this.question6 = question6;
    }

    public String getQuestion7() {
        return question7;
    }

    public void setQuestion7(String question7) {
        this.question7 = question7;
    }

    public String getQuestion8() {
        return question8;
    }

    public void setQuestion8(String question8) {
        this.question8 = question8;
    }

    public String getQuestion9() {
        return question9;
    }

    public void setQuestion9(String question9) {
        this.question9 = question9;
    }

    public String getQuestion10() {
        return question10;
    }

    public void setQuestion10(String question10) {
        this.question10 = question10;
    }

    public Integer getQuestionScore1() {
        return questionScore1;
    }

    public void setQuestionScore1(Integer questionScore1) {
        this.questionScore1 = questionScore1;
    }

    public Integer getQuestionScore2() {
        return questionScore2;
    }

    public void setQuestionScore2(Integer questionScore2) {
        this.questionScore2 = questionScore2;
    }

    public Integer getQuestionScore3() {
        return questionScore3;
    }

    public void setQuestionScore3(Integer questionScore3) {
        this.questionScore3 = questionScore3;
    }

    public Integer getQuestionScore4() {
        return questionScore4;
    }

    public void setQuestionScore4(Integer questionScore4) {
        this.questionScore4 = questionScore4;
    }

    public Integer getQuestionScore5() {
        return questionScore5;
    }

    public void setQuestionScore5(Integer questionScore5) {
        this.questionScore5 = questionScore5;
    }

    public Integer getQuestionScore6() {
        return questionScore6;
    }

    public void setQuestionScore6(Integer questionScore6) {
        this.questionScore6 = questionScore6;
    }

    public Integer getQuestionScore7() {
        return questionScore7;
    }

    public void setQuestionScore7(Integer questionScore7) {
        this.questionScore7 = questionScore7;
    }

    public Integer getQuestionScore8() {
        return questionScore8;
    }

    public void setQuestionScore8(Integer questionScore8) {
        this.questionScore8 = questionScore8;
    }

    public Integer getQuestionScore9() {
        return questionScore9;
    }

    public void setQuestionScore9(Integer questionScore9) {
        this.questionScore9 = questionScore9;
    }

    public Integer getQuestionScore10() {
        return questionScore10;
    }

    public void setQuestionScore10(Integer questionScore10) {
        this.questionScore10 = questionScore10;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getScoreAlias() {
        return scoreAlias;
    }

    public void setScoreAlias(String scoreAlias) {
        this.scoreAlias = scoreAlias;
    }
    
}
