package com.wsda.project.model;

import java.io.Serializable;

/**
 * 系统部门
 */
public class Departement implements Serializable {
    private String departmentCode;//部门编号 	VARCHAR2
    private String name;//部门名称 	VARCHAR2
    private String setupDate;//设置日期 	DATE
    private String logoutDate;//注销时间 	DATE 	 	比如临时部门
    private String parentCode;//上一级部门编号 	VARCHAR2
    private String organizationCode;//组织机构代码 	VARCHAR2
    private String manageruserCode;//部门主管用户编号 	VARCHAR2
    private String telePhone;//电话 	VARCHAR2
    private String telePhone1;//电话 1 	VARCHAR2
    private String fax;//传真 	VARCHAR2
    private String placeCode;//所在地编号 	VARCHAR2
    private String postCode;//邮政编码 	VARCHAR2
    private String district;//地区 	VARCHAR2
    private String street;//街道 	VARCHAR2
    private String createby;//创建人 	VARCHAR2
    private String createDate;//创建日期 	DATE
    private String levels;//等级 	VARCHAR2
    private String password;//密码 	VARCHAR2
    private String serial; //序号 	VARCHAR2
    private String url; //链接 	VARCHAR2

    public Departement() {
    }

    public Departement(String departmentCode, String name, String setupDate, String logoutDate, String parentCode, String organizationCode, String manageruserCode, String telePhone, String telePhone1, String fax, String placeCode, String postCode, String district, String street, String createby, String createDate, String levels, String password, String serial, String url) {
        this.departmentCode = departmentCode;
        this.name = name;
        this.setupDate = setupDate;
        this.logoutDate = logoutDate;
        this.parentCode = parentCode;
        this.organizationCode = organizationCode;
        this.manageruserCode = manageruserCode;
        this.telePhone = telePhone;
        this.telePhone1 = telePhone1;
        this.fax = fax;
        this.placeCode = placeCode;
        this.postCode = postCode;
        this.district = district;
        this.street = street;
        this.createby = createby;
        this.createDate = createDate;
        this.levels = levels;
        this.password = password;
        this.serial = serial;
        this.url = url;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSetupDate() {
        return setupDate;
    }

    public void setSetupDate(String setupDate) {
        this.setupDate = setupDate;
    }

    public String getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(String logoutDate) {
        this.logoutDate = logoutDate;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getManageruserCode() {
        return manageruserCode;
    }

    public void setManageruserCode(String manageruserCode) {
        this.manageruserCode = manageruserCode;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getTelePhone1() {
        return telePhone1;
    }

    public void setTelePhone1(String telePhone1) {
        this.telePhone1 = telePhone1;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
