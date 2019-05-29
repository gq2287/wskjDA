package com.wsda.project.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TableViewMapper {
    //获取视图列表
    List<Map<String,String>> getTableView(@Param("tableCode") String tableCode);
    //获取最大的序号
    Integer getSerialMax(@Param("tableCode") String tableCode);

    //查询实体表展示内容
    List<Map<String,String>> getTableInfo(@Param("tableName")String tableName,@Param("columnMap")Map<String,String> columnMap);
    //获取实体表名
    String getTableByTableCode(@Param("tableCode") String tableCode);
    //获取录入界面
    List<Map<String,String>> getInputCard(@Param("tableCode") String tableCode);
}
