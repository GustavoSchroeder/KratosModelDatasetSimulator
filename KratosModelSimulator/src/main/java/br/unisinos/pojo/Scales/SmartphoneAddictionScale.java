package br.unisinos.pojo.Scales;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author gustavo_schroeder
 * Datasets for Smartphone Addiction and Cyberbullying Prevalence in Early Adolescents
 * https://data.mendeley.com/datasets/m6s3d8kvsm
 */

@Entity
public class SmartphoneAddictionScale implements Serializable {
    private Long id;
    private String gender;
    private Integer resp1;
    private Integer resp2;
    private Integer resp3;
    private Integer resp4;
    private Integer resp5;
    private Integer resp6;
    private Integer resp7;
    private Integer resp8;
    private Integer resp9;
    private Integer resp10;
    private Integer total;
    private Integer result;

    public SmartphoneAddictionScale() {
    }

    public SmartphoneAddictionScale(Long id, String gender, 
            Integer resp1, Integer resp2, Integer resp3, Integer resp4, 
            Integer resp5, Integer resp6, Integer resp7, Integer resp8, 
            Integer resp9, Integer resp10, Integer total, Integer result) {
        this.id = id;
        this.gender = gender;
        this.resp1 = resp1;
        this.resp2 = resp2;
        this.resp3 = resp3;
        this.resp4 = resp4;
        this.resp5 = resp5;
        this.resp6 = resp6;
        this.resp7 = resp7;
        this.resp8 = resp8;
        this.resp9 = resp9;
        this.resp10 = resp10;
        this.total = total;
        this.result = result;
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getResp1() {
        return resp1;
    }

    public void setResp1(Integer resp1) {
        this.resp1 = resp1;
    }

    public Integer getResp2() {
        return resp2;
    }

    public void setResp2(Integer resp2) {
        this.resp2 = resp2;
    }

    public Integer getResp3() {
        return resp3;
    }

    public void setResp3(Integer resp3) {
        this.resp3 = resp3;
    }

    public Integer getResp4() {
        return resp4;
    }

    public void setResp4(Integer resp4) {
        this.resp4 = resp4;
    }

    public Integer getResp5() {
        return resp5;
    }

    public void setResp5(Integer resp5) {
        this.resp5 = resp5;
    }

    public Integer getResp6() {
        return resp6;
    }

    public void setResp6(Integer resp6) {
        this.resp6 = resp6;
    }

    public Integer getResp7() {
        return resp7;
    }

    public void setResp7(Integer resp7) {
        this.resp7 = resp7;
    }

    public Integer getResp8() {
        return resp8;
    }

    public void setResp8(Integer resp8) {
        this.resp8 = resp8;
    }

    public Integer getResp9() {
        return resp9;
    }

    public void setResp9(Integer resp9) {
        this.resp9 = resp9;
    }

    public Integer getResp10() {
        return resp10;
    }

    public void setResp10(Integer resp10) {
        this.resp10 = resp10;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }   
    
    
}
