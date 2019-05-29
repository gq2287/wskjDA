package com.wsda.project.service;

import com.wsda.project.model.Tree;

import java.util.List;
import java.util.Map;

public interface TableViewService {

    Map<String,Object> getTableView(String tableCode, int page, int size);
    Tree getTreeMenu();
    List<Map<String,String>> getInputCard(String tableCode);


}
