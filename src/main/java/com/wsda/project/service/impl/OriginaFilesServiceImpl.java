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
            System.err.println("查询视图列失败：" + e.getMessage() + "tableCode");
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
        bool=tableViewMapper.upArchivesYuanWenCountByRecordCode(tableName,recordCode,String.valueOf(oldCount+count));//修改档案的原文数量
        OriginalFiles originalFiles=null;
        if(parmsMap.get("type")!=null){
            if("0".equals(parmsMap.get("type"))){//pdf文件
                originalFiles =new OriginalFiles();
                originalFiles.setFILECODE(StringUtil.getUuid());//唯一编号
                originalFiles.setRECORDCODE(recordCode);//档案表条目编号
                if(parmsMap.get("originFileName")!=null){
                    String originFileName=parmsMap.get("originFileName");//原文名称
                    originalFiles.setFILENAME(originFileName);//文件原文名称
                    originalFiles.setMAINTITLE(originFileName);//文件题名
                }
                if(parmsMap.get("originFileName")!=null){
                    String originFileName=parmsMap.get("originFileName");
                    originFileName=originFileName.substring(originFileName.lastIndexOf(".")+1,originFileName.length());//原文后缀
                    originalFiles.setFILETYPE(originFileName);//文件原文名称
                }
                if(parmsMap.get("fileSize")!=null){
                    String fileSize=parmsMap.get("fileSize");
                    originalFiles.setFILELENGTH(fileSize);//文件长度
                }
                if(parmsMap.get("fileSize")!=null){
                    String fileSize=parmsMap.get("fileSize");
                    originalFiles.setFILELENGTH(fileSize);//文件长度
                }
                if(parmsMap.get("pdfPath")!=null){
                    String pdfPath=parmsMap.get("pdfPath");
                    originalFiles.setPDFPATH(pdfPath);//pdf路径
                }
                originalFiles.setUPLOADTIME("TO_DATE('"+StringUtil.getDate(3)+"','YYYY-MM-DD')");//上传时间
                originalFiles.setTRASHSTATUS("0");//未删除的文件 0未删除，1删除
            }else{//没有pdf文件
                originalFiles =new OriginalFiles();
                originalFiles.setFILECODE(StringUtil.getUuid());//唯一编号
                originalFiles.setRECORDCODE(recordCode);//档案表条目编号
                if(parmsMap.get("originFileName")!=null){
                    String originFileName=parmsMap.get("originFileName");//原文名称
                    originalFiles.setFILENAME(originFileName);//文件原文名称
                    originalFiles.setMAINTITLE(originFileName);//文件题名
                }
                if(parmsMap.get("originFileName")!=null){
                    String originFileName=parmsMap.get("originFileName");
                    originFileName=originFileName.substring(originFileName.lastIndexOf(".")+1,originFileName.length());//原文后缀
                    originalFiles.setFILETYPE(originFileName);//文件原文后缀
                }
                if(parmsMap.get("fileSize")!=null){
                    String fileSize=parmsMap.get("fileSize");
                    originalFiles.setFILELENGTH(fileSize);//文件长度
                }
                if(parmsMap.get("fileSize")!=null){
                    String fileSize=parmsMap.get("fileSize");
                    originalFiles.setFILELENGTH(fileSize);//文件长度
                }
                originalFiles.setUPLOADTIME("TO_DATE('"+StringUtil.getDate(3)+"','YYYY-MM-DD')");//上传时间
                originalFiles.setTRASHSTATUS("0");//未删除的文件 0未删除，1删除
            }
            if(parmsMap.get("originFilePath")!=null){
                String originFilePath=parmsMap.get("originFilePath");
                originalFiles.setORIGINAPATH(originFilePath);//原文存放路径
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

    @Override
    public Map<String,String> getPDFUrlByFileCode(String fileCode) {
        return originaFilesMapper.getPDFUrlByFileCode(fileCode);
    }

}
