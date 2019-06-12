package com.wsda.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsda.project.dao.OriginaFilesMapper;
import com.wsda.project.dao.TableViewMapper;
import com.wsda.project.service.OriginaFilesService;
import org.springframework.stereotype.Service;

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
                PageInfo<Map<String, String>> originaFilePageInfo = new PageInfo<>(originaFilesMapper.getFilesByRecordCode(tableName,recordCode,"0"));
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
    @Override
    public boolean upLoadFiles(Map<String,String> parmsMap, String tableCode,String recordCode,int count) {
        boolean bool=true;
        String tableName=tableViewMapper.getTableNameByTableCode(tableCode);//实体表名
        int oldCount=tableViewMapper.getYuanWenCountByRecordCode(tableName,recordCode);//查询当前条目的原文数量
        bool=tableViewMapper.upArchivesYuanWenCountByRecordCode(tableName,recordCode,String.valueOf(oldCount+count));//修改档案的原文数量
        if(parmsMap.get("pdfPath")!=null){

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

}
