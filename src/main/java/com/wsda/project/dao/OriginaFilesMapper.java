package com.wsda.project.dao;

import com.wsda.project.model.OriginalFiles;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OriginaFilesMapper {
//    获取指定档案旗下文件
    List<Map<String,String>> getFilesByRecordCode(@Param("tableName") String tableName,@Param("recordCode") String recordCode,@Param("type") String type);

    Map<String,String> getUpLoadFilePath();

//    添加原文纪录
    boolean addUpLoadFileOriginaFiles(OriginalFiles parmsMap);

    OriginalFiles getPDFUrlByFileCode(@Param("fileCode")String fileCode);
//    获取指定条目原文
    OriginalFiles getOrigianFileInfoByFileCode(@Param("fileCode")String fileCode);
    //    删除
    Integer delOrigianFileByFileCode(@Param("fileCode")String fileCode);
//    修改水印图片
    boolean upOriginalmanagesettingBywatermarkTxt(@Param("storeId")String storeId,@Param("watermarkTxt")String watermarkTxt);

//    添加水印路径
    boolean upWatermarkPath(@Param("fileCode")String fileCode,@Param("watermarkPath")String watermarkPath);
}
