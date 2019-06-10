package com.wsda.project.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OriginaFilesMapper {

    List<Map<String,String>> getFilesByRecordCode(@Param("recordCode") String recordCode);
}
