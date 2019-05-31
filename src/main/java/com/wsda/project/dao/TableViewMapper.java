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
    List<Map<String,String>> getTableInfo(@Param("tableName")String tableName,@Param("columnMap")Map<String,String> columnMap,@Param("whereSql")String whereSql,@Param("sortSql")String sortSql);
    //获取实体表名
    String getTableByTableCode(@Param("tableCode") String tableCode);
    //获取录入界面
    List<Map<String,Object>> getInputCard(@Param("tableCode") String tableCode);
    //获取录入类型
    List<Map<String,String>> getInputTypeByTableCodeAndName(@Param("tableCode") String tableCode,@Param("name") String name);

    boolean addTableInfo(@Param("columnMap")Map<String,String> columnMap);
}
