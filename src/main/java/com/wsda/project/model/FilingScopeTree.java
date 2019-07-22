package com.wsda.project.model;

/**
 * 归档范围树菜单
 */
public class FilingScopeTree {
    private String nodeCode;//节点编号
    private String parentCode;//父级节点编号
    private String departmentName;//部门名称
    private int serial;//序号
    private String title;//标题
    private String dateOfCustody;//归档期限
    private String departmentCode;//部门编号（用不到，先放着）
    private String description;//节点描述
    private String name;//名称
    private Object children;//旗下子节点

    public FilingScopeTree() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateOfCustody() {
        return dateOfCustody;
    }

    public void setDateOfCustody(String dateOfCustody) {
        this.dateOfCustody = dateOfCustody;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getChildren() {
        return children;
    }

    public void setChildren(Object children) {
        this.children = children;
    }
}
