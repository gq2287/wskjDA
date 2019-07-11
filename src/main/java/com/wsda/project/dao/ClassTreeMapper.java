package com.wsda.project.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 模板门类层级结构表
 */
public interface ClassTreeMapper {
    //查询门类顶级节点
    List<Map<String,String>> getClassITop();
    //I，目录树根节点
    List<Map<String,String>> getClassI(Map<String, String> map);

    //C，底层门类，中间门类
    List<Map<String,String>> getClassCL(@Param("nodeCode") String nodeCode);

    //C，底层门类，中间门类 数量
    Integer getClassCLCount(@Param("nodeCode") String nodeCode);

    //根据父节点nodeCode获取实体表数据
    List<Map<String,String>> getTableByNodeCode(@Param("nodeCode") String nodeCode);


//    根据档号表实体编号获取表编号
List<Map<String,String>>  getTableColumnslByEntityCode(@Param("entityCode") String entityCode);
}
