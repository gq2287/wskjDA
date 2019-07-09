package com.wsda.project.dao;

import com.wsda.project.model.SystemNoFormat;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 档号格式设置
 */
public interface SystemNoFormatMapper {
    //获取档号列表
    List<Map<String,String>> getSystemNoFormatList();
    //获取指定底层门类 档号列表
    List<SystemNoFormat> getSystemNoFormatListByEntityCode(@Param("entityCode") String entityCode);

    //添加档号设置
    int addSystemNoFormat(SystemNoFormat systemNoFormat);
    //删除指定的
    int delSystemNoFormatByNoFormatCode(@Param("noFormatCode") String noFormatCode);
}
