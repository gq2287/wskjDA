package com.wsda.project.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemDepartementMapper {
    //获取根据部门编号获取部门
    List<SystemDepartementMapper> getSystemDepartementByDepartmentCode(@Param("departmentCode") String departmentCode);
}
