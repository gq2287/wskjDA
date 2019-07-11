package com.wsda.project.service;

import com.wsda.project.model.SystemNoFormat;

import java.util.List;
import java.util.Map;

public interface SystemNoFormatService {
    //获取档号列表
    List<Map<String,String>> getSystemNoFormatList();
    //获取指定档号
    List<SystemNoFormat> getSystemNoFormatListByEntityCode(String noFormatCode);

    //添加档号设置
    int addSystemNoFormat(List<SystemNoFormat> systemNoFormat);
    //获取档号设置列表
    List<Map<String,String>>  getSystemNoFormatColumnsByEntityCode(String entityCode);
}
