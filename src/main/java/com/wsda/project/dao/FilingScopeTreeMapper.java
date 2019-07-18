package com.wsda.project.dao;

import com.wsda.project.model.FilingScopeTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilingScopeTreeMapper {
    //获取全部节点
    List<FilingScopeTree> getAllFilingScopes();
    //获取指定节点
    FilingScopeTree getFilingScopesByNodeCode(@Param("nodeCode")String nodeCode);
    //添加档案范围条目
    int addFilingScope(FilingScopeTree filingScopeTree);
    //删除档案范围条目
    int delFilingScopeByNodeCode(@Param("nodeCodeList")List<String> nodeCodeList);
    //删除档案范围条目
    Integer getFilingScopeMaxNodeCode(@Param("parentCode") String parentCode);
    //修改档案范围条目
    Integer upFilingScopeNodeCode(@Param("nodeCode") String nodeCode,@Param("title") String title,@Param("dateOfCustody") String dateOfCustody);
}
