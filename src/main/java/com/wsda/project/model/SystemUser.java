package com.wsda.project.model;

/**
 * 系统用户
 */
public class SystemUser {
    private String token;
    private String userName;//登录用户名称
    private String userCode;//登录用户编码
    private String password;//登录密码
    private String url;//跳转地址
    private String employeeCode;//员工编号
    private String departement;//登录用户部门
    private String sex;//性别
    private String superiorUserCode;//上级用户编号
    private String assitantUserCode;//下属用户编号
    private String isLeader;//是否是领导
    private String email;//电子信箱
    private String activeState;//活动状态
    private String officePhone;//工作电话
    private String mobile;//移动电话
    private String remark;//填写密码
    private String themeColor;//主题颜色
    public SystemUser() {
    }

    public SystemUser(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SystemUser(String token, String userName, String userCode, String password, String url, String employeeCode, String departement, String sex, String superiorUserCode, String assitantUserCode, String isLeader, String email, String activeState, String officePhone, String mobile, String remark, String themeColor) {
        this.token = token;
        this.userName = userName;
        this.userCode = userCode;
        this.password = password;
        this.url = url;
        this.employeeCode = employeeCode;
        this.departement = departement;
        this.sex = sex;
        this.superiorUserCode = superiorUserCode;
        this.assitantUserCode = assitantUserCode;
        this.isLeader = isLeader;
        this.email = email;
        this.activeState = activeState;
        this.officePhone = officePhone;
        this.mobile = mobile;
        this.remark = remark;
        this.themeColor = themeColor;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSuperiorUserCode() {
        return superiorUserCode;
    }

    public void setSuperiorUserCode(String superiorUserCode) {
        this.superiorUserCode = superiorUserCode;
    }

    public String getAssitantUserCode() {
        return assitantUserCode;
    }

    public void setAssitantUserCode(String assitantUserCode) {
        this.assitantUserCode = assitantUserCode;
    }

    public String getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(String isLeader) {
        this.isLeader = isLeader;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActiveState() {
        return activeState;
    }

    public void setActiveState(String activeState) {
        this.activeState = activeState;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }
}
