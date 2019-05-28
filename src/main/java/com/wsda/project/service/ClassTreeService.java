package com.wsda.project.service;

import com.wsda.project.model.Tree;

import java.util.List;
import java.util.Map;

public interface ClassTreeService {
    /**
     * 树菜单
     * @return
     */
    Tree getTreeMenu();
    /**
     * 获取数据字典表
     * @return
     */
    List<Map<String,String>> getAllDictionaryData();
}
