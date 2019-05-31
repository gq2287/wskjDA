package com.wsda.project.model;

import java.io.Serializable;

/**
 * 系统部门
 */
public class Departement implements Serializable {
    private String DEPARTMENTCODE;//部门编号 	VARCHAR2
    private String NAME;//部门名称 	VARCHAR2
    private String SETUPDATE;//设置日期 	DATE
    private String LOGOUTDATE;//注销时间 	DATE 	 	比如临时部门
    private String PARENTCODE;//上一级部门编号 	VARCHAR2
    private String ORGANIZATIONCODE;//组织机构代码 	VARCHAR2
    private String MANAGERUSERCODE;//部门主管用户编号 	VARCHAR2
    private String TELEPHONE;//电话 	VARCHAR2
    private String TELEPHONE1;//电话 1 	VARCHAR2
    private String FAX;//传真 	VARCHAR2
    private String PLACECODE;//所在地编号 	VARCHAR2
    private String POSTCODE;//邮政编码 	VARCHAR2
    private String DISTRICT;//地区 	VARCHAR2
    private String STREET;//街道 	VARCHAR2
    private String CREATEBY;//创建人 	VARCHAR2
    private String CREATEDATE;//创建日期 	DATE
    private String LEVELS;//等级 	VARCHAR2
    private String PASSWORD;//密码 	VARCHAR2
    private String SERIAL; //序号 	VARCHAR2
    private String URL; //链接 	VARCHAR2

    public Departement() {
    }

    public Departement(String DEPARTMENTCODE, String NAME, String SETUPDATE, String LOGOUTDATE, String PARENTCODE, String ORGANIZATIONCODE, String MANAGERUSERCODE, String TELEPHONE, String TELEPHONE1, String FAX, String PLACECODE, String POSTCODE, String DISTRICT, String STREET, String CREATEBY, String CREATEDATE, String LEVELS, String PASSWORD, String SERIAL, String URL) {
        this.DEPARTMENTCODE = DEPARTMENTCODE;
        this.NAME = NAME;
        this.SETUPDATE = SETUPDATE;
        this.LOGOUTDATE = LOGOUTDATE;
        this.PARENTCODE = PARENTCODE;
        this.ORGANIZATIONCODE = ORGANIZATIONCODE;
        this.MANAGERUSERCODE = MANAGERUSERCODE;
        this.TELEPHONE = TELEPHONE;
        this.TELEPHONE1 = TELEPHONE1;
        this.FAX = FAX;
        this.PLACECODE = PLACECODE;
        this.POSTCODE = POSTCODE;
        this.DISTRICT = DISTRICT;
        this.STREET = STREET;
        this.CREATEBY = CREATEBY;
        this.CREATEDATE = CREATEDATE;
        this.LEVELS = LEVELS;
        this.PASSWORD = PASSWORD;
        this.SERIAL = SERIAL;
        this.URL = URL;
    }

    public String getDEPARTMENTCODE() {
        return DEPARTMENTCODE;
    }

    public void setDEPARTMENTCODE(String DEPARTMENTCODE) {
        this.DEPARTMENTCODE = DEPARTMENTCODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSETUPDATE() {
        return SETUPDATE;
    }

    public void setSETUPDATE(String SETUPDATE) {
        this.SETUPDATE = SETUPDATE;
    }

    public String getLOGOUTDATE() {
        return LOGOUTDATE;
    }

    public void setLOGOUTDATE(String LOGOUTDATE) {
        this.LOGOUTDATE = LOGOUTDATE;
    }

    public String getPARENTCODE() {
        return PARENTCODE;
    }

    public void setPARENTCODE(String PARENTCODE) {
        this.PARENTCODE = PARENTCODE;
    }

    public String getORGANIZATIONCODE() {
        return ORGANIZATIONCODE;
    }

    public void setORGANIZATIONCODE(String ORGANIZATIONCODE) {
        this.ORGANIZATIONCODE = ORGANIZATIONCODE;
    }

    public String getMANAGERUSERCODE() {
        return MANAGERUSERCODE;
    }

    public void setMANAGERUSERCODE(String MANAGERUSERCODE) {
        this.MANAGERUSERCODE = MANAGERUSERCODE;
    }

    public String getTELEPHONE() {
        return TELEPHONE;
    }

    public void setTELEPHONE(String TELEPHONE) {
        this.TELEPHONE = TELEPHONE;
    }

    public String getTELEPHONE1() {
        return TELEPHONE1;
    }

    public void setTELEPHONE1(String TELEPHONE1) {
        this.TELEPHONE1 = TELEPHONE1;
    }

    public String getFAX() {
        return FAX;
    }

    public void setFAX(String FAX) {
        this.FAX = FAX;
    }

    public String getPLACECODE() {
        return PLACECODE;
    }

    public void setPLACECODE(String PLACECODE) {
        this.PLACECODE = PLACECODE;
    }

    public String getPOSTCODE() {
        return POSTCODE;
    }

    public void setPOSTCODE(String POSTCODE) {
        this.POSTCODE = POSTCODE;
    }

    public String getDISTRICT() {
        return DISTRICT;
    }

    public void setDISTRICT(String DISTRICT) {
        this.DISTRICT = DISTRICT;
    }

    public String getSTREET() {
        return STREET;
    }

    public void setSTREET(String STREET) {
        this.STREET = STREET;
    }

    public String getCREATEBY() {
        return CREATEBY;
    }

    public void setCREATEBY(String CREATEBY) {
        this.CREATEBY = CREATEBY;
    }

    public String getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(String CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public String getLEVELS() {
        return LEVELS;
    }

    public void setLEVELS(String LEVELS) {
        this.LEVELS = LEVELS;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(String SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
