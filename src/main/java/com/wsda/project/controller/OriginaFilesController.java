package com.wsda.project.controller;

import com.github.pagehelper.PageInfo;
import com.wsda.project.model.ResponseResult;
import com.wsda.project.service.impl.OriginaFilesServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@Api("原文Controller")
public class OriginaFilesController {
    private Logger logger = LoggerFactory.getLogger(OriginaFilesController.class);
    @Resource
    private OriginaFilesServiceImpl originaFilesService;
    @ApiOperation(value = "获取部门树", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getOriginaFileSByRecordCode", method = RequestMethod.GET)
    public ResponseResult getOriginaFileSByRecordCode(String tableCode,String recordCode) {
        PageInfo<Map<String, String>> pageFileDate=originaFilesService.getFilesByRecordCode(tableCode,recordCode);
        if(pageFileDate!=null){
            return new ResponseResult(ResponseResult.OK, "获取部门树成功", pageFileDate, true);
        }else{
            return new ResponseResult(ResponseResult.OK, "获取部门树失败", pageFileDate, false);
        }
    }
}
