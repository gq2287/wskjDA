package com.wsda.project.model;

/**
 * 字典实体
 * */
public class Dictionary {
    private String DICTIONARYCODE; //	字典编号
    private String DICTIONARYCATALOGCODE; 	//字典目录编号
    private String SERIAL;// 	顺序号
    private String CODE;	//代号
    private String NAME; //	项名称
    private String TYPE;	//类型
    private String REMARK;	//项注释
    private String DEFAULTVALUE;//	默认值

    public Dictionary() {
    }

    public Dictionary(String DICTIONARYCODE, String DICTIONARYCATALOGCODE, String SERIAL, String CODE, String NAME, String TYPE, String REMARK, String DEFAULTVALUE) {
        this.DICTIONARYCODE = DICTIONARYCODE;
        this.DICTIONARYCATALOGCODE = DICTIONARYCATALOGCODE;
        this.SERIAL = SERIAL;
        this.CODE = CODE;
        this.NAME = NAME;
        this.TYPE = TYPE;
        this.REMARK = REMARK;
        this.DEFAULTVALUE = DEFAULTVALUE;
    }

    public String getDICTIONARYCODE() {
        return DICTIONARYCODE;
    }

    public void setDICTIONARYCODE(String DICTIONARYCODE) {
        this.DICTIONARYCODE = DICTIONARYCODE;
    }

    public String getDICTIONARYCATALOGCODE() {
        return DICTIONARYCATALOGCODE;
    }

    public void setDICTIONARYCATALOGCODE(String DICTIONARYCATALOGCODE) {
        this.DICTIONARYCATALOGCODE = DICTIONARYCATALOGCODE;
    }

    public String getSERIAL() {
        return SERIAL;
    }

    public void setSERIAL(String SERIAL) {
        this.SERIAL = SERIAL;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getDEFAULTVALUE() {
        return DEFAULTVALUE;
    }

    public void setDEFAULTVALUE(String DEFAULTVALUE) {
        this.DEFAULTVALUE = DEFAULTVALUE;
    }
}
