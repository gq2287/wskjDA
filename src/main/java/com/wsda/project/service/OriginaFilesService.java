package com.wsda.project.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface OriginaFilesService {
    PageInfo<Map<String,String>> getFilesByRecordCode(String tableCode,String recordCode, int pageNum,int PageSize);
}
