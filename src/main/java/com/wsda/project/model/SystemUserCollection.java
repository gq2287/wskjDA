package com.wsda.project.model;

/**
 * 用户收藏档案
 */
public class SystemUserCollection {
    private String id;//编号
    private String userCode;//用户编号
    private String archivesCode;//档案编号
    private String tableCode;//实体表编号
    private String createTime;//收藏时间
    private String remark;//备注
    private String collectionFilesId;//收藏夹编号

    public SystemUserCollection() {
    }

    public SystemUserCollection(String id, String userCode, String archivesCode, String tableCode, String createTime, String remark, String collectionFilesId) {
        this.id = id;
        this.userCode = userCode;
        this.archivesCode = archivesCode;
        this.tableCode = tableCode;
        this.createTime = createTime;
        this.remark = remark;
        this.collectionFilesId = collectionFilesId;
    }

    public String getCollectionFilesId() {
        return collectionFilesId;
    }

    public void setCollectionFilesId(String collectionFilesId) {
        this.collectionFilesId = collectionFilesId;
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
