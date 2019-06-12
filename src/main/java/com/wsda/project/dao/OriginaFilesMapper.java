package com.wsda.project.dao;

import com.wsda.project.model.OriginalFiles;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OriginaFilesMapper {

    List<Map<String,String>> getFilesByRecordCode(@Param("tableName") String tableName,@Param("recordCode") String recordCode,@Param("type") String type);

    Map<String,String> getUpLoadFilePath();

//    添加原文纪录
    boolean addUpLoadFileOriginaFiles(OriginalFiles parmsMap);

    Map<String,String> getPDFUrlByFileCode(@Param("fileCode")String fileCode);
}
