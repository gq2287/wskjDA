package com.wsda.project.model;

import java.io.Serializable;

/**
 * 表档号
 */
public class SystemNoFormat implements Serializable {
    private String NOFORMATCODE;//档号格式编号
    private String ENTITYCODE;//实体编号
    private String COLUMNNAME;//列名
    private String CHINESENAME;//中文名称
    private String LENTH;//长度
    private String NO;//号码 顺序号
    private String STYLE;//风格
    private String SEPARATOR;//分隔符号

    public SystemNoFormat() {
    }

    public SystemNoFormat(String NOFORMATCODE, String ENTITYCODE, String COLUMNNAME, String CHINESENAME, String LENTH, String NO, String STYLE, String SEPARATOR) {
        this.NOFORMATCODE = NOFORMATCODE;
        this.ENTITYCODE = ENTITYCODE;
        this.COLUMNNAME = COLUMNNAME;
        this.CHINESENAME = CHINESENAME;
        this.LENTH = LENTH;
        this.NO = NO;
        this.STYLE = STYLE;
        this.SEPARATOR = SEPARATOR;
    }

    public String getNOFORMATCODE() {
        return NOFORMATCODE;
    }

    public void setNOFORMATCODE(String NOFORMATCODE) {
        this.NOFORMATCODE = NOFORMATCODE;
    }

    public String getENTITYCODE() {
        return ENTITYCODE;
    }

    public void setENTITYCODE(String ENTITYCODE) {
        this.ENTITYCODE = ENTITYCODE;
    }

    public String getCOLUMNNAME() {
        return COLUMNNAME;
    }

    public void setCOLUMNNAME(String COLUMNNAME) {
        this.COLUMNNAME = COLUMNNAME;
    }

    public String getCHINESENAME() {
        return CHINESENAME;
    }

    public void setCHINESENAME(String CHINESENAME) {
        this.CHINESENAME = CHINESENAME;
    }

    public String getLENTH() {
        return LENTH;
    }

    public void setLENTH(String LENTH) {
        this.LENTH = LENTH;
    }

    public String getNO() {
        return NO;
    }

    public void setNO(String NO) {
        this.NO = NO;
    }

    public String getSTYLE() {
        return STYLE;
    }

    public void setSTYLE(String STYLE) {
        this.STYLE = STYLE;
    }

    public String getSEPARATOR() {
        return SEPARATOR;
    }

    public void setSEPARATOR(String SEPARATOR) {
        this.SEPARATOR = SEPARATOR;
    }
}
