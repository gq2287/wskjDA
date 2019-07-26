package com.wsda.project.model;

/**
 * 系统日志
 * 存放系统的日志信息
 */
public class SystemLog {
    private String logCode ;//编号
    private String macNumber;//操作 IP 地址
    private String userCode ;//用户编号
    private String time;//操作被记录的时间
    private String system;//模块 用户操作的功能节点名称（功能模块）
    private String type ;//类型 默认值“system”
    private String content;//具体的操作结果说明

    public SystemLog() {
    }

    public SystemLog(String logCode, String macNumber, String userCode, String time, String system, String type, String content) {
        this.logCode = logCode;
        this.macNumber = macNumber;
        this.userCode = userCode;
        this.time = time;
        this.system = system;
        this.type = type;
        this.content = content;
    }

    public String getLogCode() {
        return logCode;
    }

    public void setLogCode(String logCode) {
        this.logCode = logCode;
    }

    public String getMacNumber() {
        return macNumber;
    }

    public void setMacNumber(String macNumber) {
        this.macNumber = macNumber;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
