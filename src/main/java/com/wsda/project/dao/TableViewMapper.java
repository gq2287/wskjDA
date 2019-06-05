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
    List<Map<String,String>> getTableInfo(@Param("tableName")String tableName,@Param("columnMap")Map<String,String> columnMap,@Param("whereSql")String whereSql,@Param("sortSql")String sortSql,@Param("type")String type);
    //获取实体表名
    String getTableNameByTableCode(@Param("tableCode") String tableCode);
    //获取录入界面
    List<Map<String,Object>> getInputCard(@Param("tableCode") String tableCode);
    //获取录入类型
    List<Map<String,String>> getInputTypeByTableCodeAndName(@Param("tableCode") String tableCode,@Param("name") String name);
    //添加档案纪录
    boolean addTableInfo(@Param("tableName")String tableName,@Param("columns")List<String> columns,@Param("values")List<String> values);
    //恢复删除档案纪录
    boolean upArchives(@Param("tableName")String tableName,@Param("recordCode") String recordCode,@Param("trashStatus") String trashStatus);
    //修改档案
    boolean upArchivesByRecordCode(@Param("tableName")String tableName,@Param("recordCode") String recordCode,@Param("parms") Map<String,String> parms);
    //查询档案条目
    Map<String,String> getArchivesByRecordCode(@Param("tableName")String tableName,@Param("recordCode") String recordCode);
//    获取档案字段类型
    List<Map<String,Object>> getTypeByTableCode(@Param("tableCode")String tableCode);

    List<String> getGroup(@Param("tableName")String tableName,@Param("group") String group);
}
