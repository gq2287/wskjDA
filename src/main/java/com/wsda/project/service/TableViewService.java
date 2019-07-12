package com.wsda.project.service;

import com.wsda.project.model.Tree;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TableViewService {

    Map<String,Object> getTableView(String tableCode, int page, int size, List<Map<String,String>> conditions, List<Map<String,String>> sorts, String type, HttpServletRequest request);
    Tree getTreeMenu();
    List<Map<String,Object>> getInputCard(String tableCode);

    boolean addTableInfo(Map<String,String> info);

    List<Map<String,String>> getAllSystemFonds();

    List<Tree> getAllSystemFondsTree(String fondsCode);

    boolean upArchives(String tableCode,List<String> recordCode,String trashStatus,int type) throws SQLException, Exception;

    Map<String,String> getArchives(String tableCode,String recordCode,int type);

    boolean upArchivesByRecordCode(String tableCode,String recordCode,Map<String,String> parms,int type);
    //分组
    List<Object> getGroup(String tableCode,List<String> group );

//    待归档
    boolean updateArchivesFiledByRecordCode(String tableCode,List<Map<String,String>> parms);
//    已归档
    boolean updateArchivesByRecordCode(String tableCode,List<Map<String,String>> parms);
//    不归档
    boolean updateNoArchivesByRecordCode(String tableCode,List<Map<String,String>> parms);

}
