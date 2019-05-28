package com.wsda.project.service;

import com.wsda.project.model.Tree;

import java.util.List;
import java.util.Map;

public interface TableViewService {

    List<Map<String,Object>> getTableView(String tableCode);
    Tree getTreeMenu();


}
