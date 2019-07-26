package com.wsda.project.service;

import com.wsda.project.model.OriginalFiles;

import java.util.Map;

public interface OriginaFilesService {
    Map<String, Object> getFilesByRecordCode(String tableCode, String recordCode, int pageNum, int PageSize, String type);

    //文件上传
    boolean addUpLoadFiles(Map<String, String> parmsMap, String tableCode, String recordCode, int count);

    //获取文件上传的路径信息
    Map<String, String> getUpLoadFilePath();

    //获取pdf地址
    OriginalFiles getPDFUrlByFileCode(String fileCode);

    //    删除
    boolean delOrigianFileByFileCode(String tableCode, String fileCode);

    // 修改水印文字
    boolean upWatermarkTxt(String storeId, String watermarkTxt);

    boolean upWatermarkPath(String fileCode, String watermarkPath);

    //修改置顶状态
    boolean upTopByFileCode(String fileCode, String oldFileCode);
}
