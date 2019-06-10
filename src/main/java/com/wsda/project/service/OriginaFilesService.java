package com.wsda.project.service;

import java.util.Map;

public interface OriginaFilesService {
    Map<String,Object> getFilesByRecordCode(String tableCode,String recordCode, int pageNum,int PageSize);
}
