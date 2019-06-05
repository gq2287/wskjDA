package com.wsda.project.model;

/**
 * 分组实体
 */
public class GroupMode {
    private String chineseName;//分组字段中文名称
    private String EnglishName;//分组字段英文名称
    private String columnType;//分组字段数据类型
    private Object groupList;//分组字段集合

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public Object getGroupList() {
        return groupList;
    }

    public void setGroupList(Object groupList) {
        this.groupList = groupList;
    }

    public GroupMode() {
    }

    public GroupMode(String chineseName, String englishName, String columnType, Object groupList) {
        this.chineseName = chineseName;
        EnglishName = englishName;
        this.columnType = columnType;
        this.groupList = groupList;
    }
}
