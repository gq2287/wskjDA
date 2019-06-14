package com.wsda.project.model;

/**
 * 用户收藏
 */
public class systemUserCollection {
    private String id;//编号
    private String userCode;//用户编号
    private String archivesCode;//档案编号
    private String tableCode;//实体表编号
    private String createTime;//收藏时间
    private String remark;//备注

    public systemUserCollection() {
    }

    public systemUserCollection(String id, String userCode, String archivesCode, String tableCode, String createTime, String remark) {
        this.id = id;
        this.userCode = userCode;
        this.archivesCode = archivesCode;
        this.tableCode = tableCode;
        this.createTime = createTime;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getArchivesCode() {
        return archivesCode;
    }

    public void setArchivesCode(String archivesCode) {
        this.archivesCode = archivesCode;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
