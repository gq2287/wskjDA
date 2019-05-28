package com.wsda.project.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TableViewMapper {
    //获取视图列表
    List<Map<String,Object>> getTableView(@Param("tableCode") String tableCode);
    //获取最大的序号
    Integer getSerialMax(@Param("tableCode") String tableCode);
}
