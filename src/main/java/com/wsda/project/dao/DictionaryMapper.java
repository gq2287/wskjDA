package com.wsda.project.dao;

import com.wsda.project.model.Dictionary;

import java.util.List;

public interface DictionaryMapper {
    //获取数据字典表
    List<Dictionary> getAllDictionaryData(String code);
}
