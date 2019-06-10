package com.wsda.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsda.project.dao.OriginaFilesMapper;
import com.wsda.project.service.OriginaFilesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
@Service
public class OriginaFilesServiceImpl implements OriginaFilesService {

    @Resource
    private OriginaFilesMapper originaFilesMapper;
    @Override
    public PageInfo<Map<String, String>> getFilesByRecordCode(String tableCode, String recordCode, int pageNum,int PageSize) {
        PageHelper.startPage(pageNum,PageSize);
        PageInfo<Map<String,String>> originaFilePageInfo=new PageInfo<>(originaFilesMapper.getFilesByRecordCode(tableCode,recordCode));
        return originaFilePageInfo;
    }
}
