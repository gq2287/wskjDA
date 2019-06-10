package com.wsda.project.model;

import java.io.Serializable;

public class PDFFile implements Serializable {
    private String id;
    private String name;
    private String url;
    private String createDate;
    private String oldType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getOldType() {
        return oldType;
    }

    public void setOldType(String oldType) {
        this.oldType = oldType;
    }

    public PDFFile() {
    }
    public PDFFile(String id,String name, String url, String createDate, String oldType) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.createDate = createDate;
        this.oldType = oldType;
    }
    public PDFFile(String name, String url, String createDate, String oldType) {
        this.name = name;
        this.url = url;
        this.createDate = createDate;
        this.oldType = oldType;
    }

    @Override
    public String toString() {
        return "PDFFile{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", createDate='" + createDate + '\'' +
                ", oldType='" + oldType + '\'' +
                '}';
    }
}
