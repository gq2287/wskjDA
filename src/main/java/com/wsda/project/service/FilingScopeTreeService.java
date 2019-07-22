package com.wsda.project.service;

import com.wsda.project.model.FilingScopeTree;

import java.util.List;

public interface FilingScopeTreeService {
    //归档范围菜单
    List<FilingScopeTree> getFilingScopeTreeMenu();

    List<FilingScopeTree> getFilingScopeTreeByParms(FilingScopeTree filingScopeTree);
    //添加档案范围条目
    boolean addFilingScope(FilingScopeTree filingScopeTree);

    //删除档案范围条目
    boolean delFilingScopeByNodeCode(String nodeCode);

    //获取最大的编号
    int getFilingScopeMaxNodeCode(String parentCode);
    //修改档案范围条目
    int upFilingScopeNodeCode(String nodeCode,String title,String dateOfCustody);
}
