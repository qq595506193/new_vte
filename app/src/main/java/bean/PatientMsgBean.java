package bean;

import java.io.Serializable;
import java.util.List;

public class PatientMsgBean implements Serializable {

    private String PATIENT_NAME;// 患者姓名
    private String tk;// tk
    private String PATIENT_SEX;// 患者性别
    private String BED_NUMBER;// 患者床号
    private String DEPARTMENT_ID;// 科室
    private String CURRENT_RISK_LEVEL;// 危险等级
    private String CARE_UNIT;// 单元

    public String getCARE_UNIT() {
        return CARE_UNIT;
    }

    public void setCARE_UNIT(String CARE_UNIT) {
        this.CARE_UNIT = CARE_UNIT;
    }

    public String getTk() {
        return tk;
    }

    public void setTk(String tk) {
        this.tk = tk;
    }

    public String getCURRENT_RISK_LEVEL() {
        return CURRENT_RISK_LEVEL;
    }

    public void setCURRENT_RISK_LEVEL(String CURRENT_RISK_LEVEL) {
        this.CURRENT_RISK_LEVEL = CURRENT_RISK_LEVEL;
    }


    public String getPATIENT_NAME() {
        return PATIENT_NAME;
    }

    public void setPATIENT_NAME(String PATIENT_NAME) {
        this.PATIENT_NAME = PATIENT_NAME;
    }

    public String getPATIENT_SEX() {
        return PATIENT_SEX;
    }

    public void setPATIENT_SEX(String PATIENT_SEX) {
        this.PATIENT_SEX = PATIENT_SEX;
    }

    public String getBED_NUMBER() {
        return BED_NUMBER;
    }

    public void setBED_NUMBER(String BED_NUMBER) {
        this.BED_NUMBER = BED_NUMBER;
    }

    public String getDEPARTMENT_ID() {
        return DEPARTMENT_ID;
    }

    public void setDEPARTMENT_ID(String DEPARTMENT_ID) {
        this.DEPARTMENT_ID = DEPARTMENT_ID;
    }
}
