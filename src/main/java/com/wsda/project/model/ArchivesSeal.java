package com.wsda.project.model;

/**
 * 归档章
 */
public class ArchivesSeal {
    private  String id ;
    private  String tableCode;
    private  String columnContent;
    private  int serial;
    private  String state;

    public ArchivesSeal() {
    }

    public ArchivesSeal(String id, String tableCode, String columnContent, int serial,String state) {
        this.id = id;
        this.tableCode = tableCode;
        this.columnContent = columnContent;
        this.serial = serial;
        this.state=state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getColumnContent() {
        return columnContent;
    }

    public void setColumnContent(String columnContent) {
        this.columnContent = columnContent;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }
}
