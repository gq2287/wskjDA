package com.wsda.project.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SystemCataLogMapper {
    //获取全宗号
    List<Map<String,String>> getAllSystemFonds();

    //获取系统全宗信息
    List<Map<String,String>> getAllSystemCataLogByFondsCode(@Param("parentCode") String parentCode,@Param("fondsCode") String fondsCode);
}
