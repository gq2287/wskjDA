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
    private OriginaFilesMapper originaFilesMapper;

    @Override
    public Map<String, Object> getFilesByRecordCode(String tableCode, String recordCode, int pageNum, int PageSize) {
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
            PageHelper.startPage(pageNum, PageSize);
            PageInfo<Map<String, String>> originaFilePageInfo = new PageInfo<>(originaFilesMapper.getFilesByRecordCode(recordCode));
            parmsMap.put("tableColums", arrayList);//展示列
            parmsMap.put("originaFilePageInfo", originaFilePageInfo);//实体表内容
        } catch (Exception e) {
            System.err.println("查询视图列失败：" + e.getMessage() + "tableCode");
        }
        return parmsMap;
    }
}
