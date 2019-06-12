package com.wsda.project.service;

import java.util.Map;

public interface OriginaFilesService {
    Map<String,Object> getFilesByRecordCode(String tableCode,String recordCode, int pageNum,int PageSize,String type);

    //文件上传
    boolean upLoadFiles(Map<String,String> parmsMap,String tableCode,String recordCode,int count);

    //获取文件上传的路径信息
    Map<String,String> getUpLoadFilePath();
}
