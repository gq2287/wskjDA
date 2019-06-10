package com.wsda.project.controller;

import com.github.pagehelper.PageInfo;
import com.wsda.project.model.ResponseResult;
import com.wsda.project.service.impl.OriginaFilesServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "获取档案下原文信息", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getOriginaFileSByRecordCode", method = RequestMethod.POST)
    public ResponseResult getOriginaFileSByRecordCode(@ApiParam(required = true, name = "tableCode", value = "表编号") String tableCode,
                                                      @ApiParam(required = true, name = "recordCode", value = "档案唯一编号")String recordCode,
                                                      @ApiParam(required = true, name = "pageNum", value = "当前页") int pageNum,
                                                      @ApiParam(required = true, name = "pageSize", value = "每页条目数")int pageSize) {
        PageInfo<Map<String,String>> pageFileDate=originaFilesService.getFilesByRecordCode(tableCode,recordCode,pageNum,pageSize);
        if(pageFileDate!=null){
            if(pageFileDate.getList().size()>0){
                return new ResponseResult(ResponseResult.OK, "获取原文信息成功", pageFileDate, true);
            }else{
                return new ResponseResult(ResponseResult.OK, "当前档案暂无原文可供查看", pageFileDate, true);
            }
        }else{
            return new ResponseResult(ResponseResult.OK, "获取原文信息失败", pageFileDate, false);
        }
    }
}
