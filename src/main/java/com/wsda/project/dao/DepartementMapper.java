package com.wsda.project.dao;

import com.wsda.project.model.Departement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartementMapper {
    //获取根据部门编号获取子集部门
    List<Departement> getDepartementByParentCode(@Param("parentCode") String parentCode);

    String getDepartementNameByDepartementCode(@Param("departementCode")String departementCode);

}
