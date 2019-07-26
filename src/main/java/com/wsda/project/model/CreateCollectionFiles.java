package com.wsda.project.model;

/**
 * 收藏夹
 */
public class CreateCollectionFiles {
    private String id;//编号
    private String userCode;//用户编号
    private String collectionName;//档案编号
    private String createTime;//收藏时间

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

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public CreateCollectionFiles() {
    }

    public CreateCollectionFiles(String id, String userCode, String collectionName, String createTime) {
        this.id = id;
        this.userCode = userCode;
        this.collectionName = collectionName;
        this.createTime = createTime;
    }
}
