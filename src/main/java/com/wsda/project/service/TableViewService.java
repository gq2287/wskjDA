package com.wsda.project.service;

import com.wsda.project.model.Tree;

import java.util.List;
import java.util.Map;

public interface TableViewService {

    Map<String,Object> getTableView(String tableCode, int page, int size, List<Map<String,String>> conditions, List<Map<String,String>> sorts);
    Tree getTreeMenu();
    List<Map<String,Object>> getInputCard(String tableCode);

    boolean addTableInfo(Map<String,String> info);

    List<Map<String,String>> getAllSystemFonds();

    List<Tree> getAllSystemFondsTree(String fondsCode);

}
