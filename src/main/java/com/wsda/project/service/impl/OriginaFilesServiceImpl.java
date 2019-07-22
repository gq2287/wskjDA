package com.wsda.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsda.project.dao.OriginaFilesMapper;
import com.wsda.project.dao.TableViewMapper;
import com.wsda.project.model.OriginalFiles;
import com.wsda.project.service.OriginaFilesService;
import com.wsda.project.util.DeleteFileUtil;
import com.wsda.project.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OriginaFilesServiceImpl implements OriginaFilesService {

    @Resource
    private TableViewMapper tableViewMapper;
    @Resource
    private TableViewServiceImpl tableViewService;
    @Resource
    private OriginaFilesMapper originaFilesMapper;

    /**
     * 获取指定档案旗下文件
     * @param tableCode 表编号
     * @param recordCode 档案条目编号
     * @param pageNum
     * @param PageSize
     * @param type
     * @return
     */
    @Override
    public Map<String, Object> getFilesByRecordCode(String tableCode, String recordCode, int pageNum, int PageSize,String type) {
        List<Map<String, String>> arrayList = new ArrayList<>();
        Map<String, Object> parmsMap = new HashMap<>();
        try {
            arrayList = tableViewMapper.getTableView(tableCode);//获取展示列
            Map<String, String> columnMap = new HashMap<>();
            if (arrayList.size() > 0) {
                for (int i = 0; i < arrayList.size(); i++) {
                    for (String str : arrayList.get(i).keySet()) {
                        if ("NAME".equals(str)) {
                            arrayList.get(i).put(str, arrayList.get(i).get(str).toUpperCase());//转小写
                            columnMap.put(arrayList.get(i).get(str), arrayList.get(i).get(str));
                        }
                    }
                }
            }
            //业务
            String tableName=tableViewMapper.getTableNameByTableCode(tableCode);//获取实体表名称
            if(tableName!=null){
                PageHelper.startPage(pageNum, PageSize);
                PageInfo<Map<String, String>> originaFilePageInfo = new PageInfo<>(originaFilesMapper.getFilesByRecordCode(tableName,recordCode,type));
                parmsMap.put("tableColums", arrayList);//展示列
                tableViewService.toDataByTime(arrayList,originaFilePageInfo);
                parmsMap.put("originaFilePageInfo", originaFilePageInfo);//实体表内容
            }else{
                return null;
            }
        } catch (Exception e) {
            System.err.println("原文查询视图列失败：" + e.getMessage() + "tableCode");
        }
        return parmsMap;
    }

    /**
     * 文件上传
     * @param parmsMap 包涵文件上传地址，原文保存地址，pdf保存地址
     * @param tableCode
     * @param recordCode
     * @param count
     * @return
     */
    @Transactional
    @Override
    public boolean addUpLoadFiles(Map<String,String> parmsMap, String tableCode,String recordCode,int count) {
        boolean bool=true;
        String tableName=tableViewMapper.getTableNameByTableCode(tableCode);//实体表名
        int oldCount=tableViewMapper.getYuanWenCountByRecordCode(tableName,recordCode);//查询当前条目的原文数量
        tableViewMapper.upArchivesYuanWenCountByRecordCode(tableName,recordCode,String.valueOf(oldCount+count));//修改档案的原文数量
        OriginalFiles originalFiles=null;
        if(parmsMap.get("type")!=null){
            if("0".equals(parmsMap.get("type"))){//pdf文件
                originalFiles =new OriginalFiles();
                originalFiles.setFileCode(StringUtil.getUuid());//唯一编号
                originalFiles.setRecordCode(recordCode);//档案表条目编号
                if(parmsMap.get("originFileName")!=null){
                    String originFileName=parmsMap.get("originFileName");//原文名称
                    originalFiles.setFileName(originFileName);//文件原文名称
                    originalFiles.setMainTitle(originFileName);//文件题名
                }
                if(parmsMap.get("originFileName")!=null){
                    String originFileName=parmsMap.get("originFileName");
                    originFileName=originFileName.substring(originFileName.lastIndexOf(".")+1,originFileName.length());//原文后缀
                    originalFiles.setFileType(originFileName);//文件原文名称
                }
                if(parmsMap.get("fileSize")!=null){
                    String fileSize=parmsMap.get("fileSize");
                    originalFiles.setFileLength(fileSize);//文件长度
                }
                if(parmsMap.get("fileSize")!=null){
                    String fileSize=parmsMap.get("fileSize");
                    originalFiles.setFileLength(fileSize);//文件长度
                }
                if(parmsMap.get("pdfPath")!=null){
                    String pdfPath=parmsMap.get("pdfPath");
                    originalFiles.setPdfPath(pdfPath);//pdf路径
                }
//                添加水印start
                if(parmsMap.get("watermarkPath")!=null){
                    String watermarkPath=parmsMap.get("watermarkPath");
                    originalFiles.setWatermarkPath(watermarkPath);//水印pdf路径
                }
//                添加水印end

                originalFiles.setUploadTime("TO_DATE('"+StringUtil.getDate(3)+"','YYYY-MM-DD')");//上传时间
                originalFiles.setTrashStatus("0");//未删除的文件 0未删除，1删除
            }else{//没有pdf文件
                originalFiles =new OriginalFiles();
                originalFiles.setFileCode(StringUtil.getUuid());//唯一编号
                originalFiles.setRecordCode(recordCode);//档案表条目编号
                if(parmsMap.get("originFileName")!=null){
                    String originFileName=parmsMap.get("originFileName");//原文名称
                    originalFiles.setFileName(originFileName);//文件原文名称
                    originalFiles.setMainTitle(originFileName);//文件题名
                }
                if(parmsMap.get("originFileName")!=null){
                    String originFileName=parmsMap.get("originFileName");
                    originFileName=originFileName.substring(originFileName.lastIndexOf(".")+1,originFileName.length());//原文后缀
                    originalFiles.setFileType(originFileName);//文件原文后缀
                }
                if(parmsMap.get("fileSize")!=null){
                    String fileSize=parmsMap.get("fileSize");
                    originalFiles.setFileLength(fileSize);//文件长度
                }
                if(parmsMap.get("fileSize")!=null){
                    String fileSize=parmsMap.get("fileSize");
                    originalFiles.setFileLength(fileSize);//文件长度
                }
                originalFiles.setUploadTime("TO_DATE('"+StringUtil.getDate(3)+"','YYYY-MM-DD')");//上传时间
                originalFiles.setTrashStatus("0");//未删除的文件 0未删除，1删除
            }
            if(parmsMap.get("originFilePath")!=null){
                String originFilePath=parmsMap.get("originFilePath");
                originalFiles.setOriginalFilePath(originFilePath);//原文存放路径
            }
            bool =originaFilesMapper.addUpLoadFileOriginaFiles(originalFiles);
        }else {
            if(parmsMap.get("pdfPath")!=null){
                String pdfPath=parmsMap.get("pdfPath");
                DeleteFileUtil.deleteFile(pdfPath);
            }
            bool=false;
        }
        //添加原文数据
        return bool;
    }

    /**
     * 获取文件上传路径的信息
     * @return
     */
    @Override
    public Map<String, String> getUpLoadFilePath() {
        return originaFilesMapper.getUpLoadFilePath();
    }

    /**
     * 获取指定pdf查看地址
     * @param fileCode
     * @return
     */
    @Override
    public OriginalFiles getPDFUrlByFileCode(String fileCode) {
        return originaFilesMapper.getPDFUrlByFileCode(fileCode);
    }

    /**
     * 删除原文条目
     * @param tableCode
     * @param fileCode
     * @return
     */
    @Override
    public boolean delOrigianFileByFileCode(String tableCode,String fileCode) {
        OriginalFiles originalFiles=originaFilesMapper.getOrigianFileInfoByFileCode(fileCode);//获取删除的原文条目
        if(originalFiles!=null){
            if(originalFiles.getPdfPath()!=null){//删除pdf
                DeleteFileUtil.deleteFile(originalFiles.getPdfPath());
            }
            if(originalFiles.getOriginalFilePath()!=null){//删除原文
                DeleteFileUtil.deleteFile(originalFiles.getOriginalFilePath());
            }
            if(originalFiles.getWatermarkPath()!=null){//删除水印
                DeleteFileUtil.deleteFile(originalFiles.getWatermarkPath());
            }
        }

        int result=originaFilesMapper.delOrigianFileByFileCode(fileCode);//删除原文条目
        if(result>=0){
            String name=tableViewMapper.getTableNameByTableCode(tableCode);//获取档案表
            int oldCount=tableViewMapper.getYuanWenCountByRecordCode(name,originalFiles.getRecordCode());//查询当前档案条目的原文数量
            return tableViewMapper.upArchivesYuanWenCountByRecordCode(name,originalFiles.getRecordCode(),(oldCount-1)+"");//修改原文数量
        }else {
            return false;
        }
    }

    /**
     * 修改水印文字
     * @param storeId
     * @param watermarkTxt
     * @return
     */
    @Override
    public boolean upWatermarkTxt(String storeId,String watermarkTxt) {
        return originaFilesMapper.upOriginalmanagesettingBywatermarkTxt(storeId,watermarkTxt);
    }

    /**
     * 添加水印pdf路径
     * @param fileCode
     * @param watermarkPath
     * @return
     */
    @Override
    public boolean upWatermarkPath(String fileCode, String watermarkPath) {
        return originaFilesMapper.upWatermarkPath(fileCode,watermarkPath);
    }

}
