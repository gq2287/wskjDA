package com.wsda.project.model;

import java.io.Serializable;

/**
 * 表档号
 */
public class SystemNoFormat implements Serializable {
    private String noFormatCode;//档号格式编号
    private String entityCode;//实体编号
    private String columnName;//列名
    private String chineseName;//中文名称
    private String lenth;//长度
    private String no;//号码 顺序号
    private String style;//风格
    private String separator;//分隔符号
    private String emulationShow;//展示效果
    private String type;//临时添加 供前端判断

    public SystemNoFormat() {
    }

    public SystemNoFormat(String noFormatCode, String entityCode, String columnName, String chineseName, String lenth, String no, String style, String separator, String emulationShow, String type) {
        this.noFormatCode = noFormatCode;
        this.entityCode = entityCode;
        this.columnName = columnName;
        this.chineseName = chineseName;
        this.lenth = lenth;
        this.no = no;
        this.style = style;
        this.separator = separator;
        this.emulationShow = emulationShow;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmulationShow() {
        return emulationShow;
    }

    public void setEmulationShow(String emulationShow) {
        this.emulationShow = emulationShow;
    }

    public String getNoFormatCode() {
        return noFormatCode;
    }

    public void setNoFormatCode(String noFormatCode) {
        this.noFormatCode = noFormatCode;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getLenth() {
        return lenth;
    }

    public void setLenth(String lenth) {
        this.lenth = lenth;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
