package com.wsda.project.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TableViewMapper {
    //获取视图列表
    List<Map<String, String>> getTableView(@Param("tableCode") String tableCode);

    //获取最大的序号
    Integer getSerialMax(@Param("tableCode") String tableCode);

    //查询实体表展示内容
    List<Map<String, String>> getTableInfo(@Param("tableName") String tableName, @Param("columnMap") Map<String, String> columnMap, @Param("whereSql") String whereSql, @Param("sortSql") String sortSql, @Param("type") String type, @Param("uscId") String uscId);

    //获取实体表名
    String getTableNameByTableCode(@Param("tableCode") String tableCode);

    //获取录入界面
    List<Map<String, Object>> getInputCard(@Param("tableCode") String tableCode);

    //获取录入类型
    List<Map<String, String>> getInputTypeByTableCodeAndName(@Param("tableCode") String tableCode, @Param("name") String name);

    //添加档案纪录
    boolean addTableInfo(@Param("tableName") String tableName, @Param("columns") List<String> columns, @Param("values") List<String> values);

    //恢复删除档案纪录
    boolean upArchives(@Param("tableName") String tableName, @Param("recordCode") String recordCode, @Param("trashStatus") String trashStatus, @Param("CODE") String CODE);

    //修改档案
    boolean upArchivesByRecordCode(@Param("tableName") String tableName, @Param("recordCode") String recordCode, @Param("columnMap") Map<String, String> parms, @Param("CODE") String CODE);

    //查询档案条目
    Map<String, String> getArchivesByRecordCode(@Param("tableName") String tableName, @Param("recordCode") String recordCode, @Param("CODE") String CODE);

    //    获取档案字段类型
    List<Map<String, Object>> getTypeByTableCode(@Param("tableCode") String tableCode);

    List<String> getGroup(@Param("tableName") String tableName, @Param("group") String group);

    //查看档案条目原文纪录总数
    Integer getYuanWenCountByRecordCode(@Param("tableName") String tableName, @Param("recordCode") String recordCode);

    //    修改原文数量
    Boolean upArchivesYuanWenCountByRecordCode(@Param("tableName") String tableName, @Param("recordCode") String recordCode, @Param("count") String count);

    //查询当前档案信息
    Map<String, String> getArchivesByIsArchiveFlag(Map<String, String> parms);

    //    获取录入字段名称
    List<String> getInputCardFieldName(@Param("tableCode") String tableCode);

    //批量放入待归档
    int updateArchivesFiledByRecordCode(@Param("tableName") String tableName, @Param("archivesList") List<Map<String, String>> archivesList);

    //批量放入已归档
    int updateArchivesByRecordCode(@Param("tableName") String tableName, @Param("archivesList") List<Map<String, String>> archivesList);
//根据档案主键查询他的页数或者件数不是空并且有值的
    List<String> getYSByRecordCode(@Param("tableName") String tableName, @Param("recordCodeList") List<String> recordCodeList, @Param("quantity")String quantity);
}
