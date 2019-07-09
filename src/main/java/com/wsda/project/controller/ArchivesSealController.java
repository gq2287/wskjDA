package com.wsda.project.controller;

import com.wsda.project.model.ArchivesSeal;
import com.wsda.project.model.ResponseResult;
import com.wsda.project.service.impl.ArchivesSealServiceImpl;
import com.wsda.project.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api("归档章Controller")
public class ArchivesSealController {
    private Logger logger = LoggerFactory.getLogger(ArchivesSealController.class);

    @Resource
    private ArchivesSealServiceImpl archivesSealService;

    @ApiOperation(value = "获取当前表归档章", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getArchivesSeal", method = RequestMethod.POST)
    public ResponseResult getArchivesSeal(String tableCode) {
        ArchivesSeal archivesSeal=archivesSealService.getArchivesSeal(tableCode);
        if(archivesSeal!=null){
            return new ResponseResult(ResponseResult.OK, "查询成功",archivesSeal, true);
        }else{
            return new ResponseResult(ResponseResult.OK, "当前表不存在归档章设置", false);
        }
    }

    @ApiOperation(value = "当前表添加归档章", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/addArchivesSeal", method = RequestMethod.POST)
    public ResponseResult addArchivesSeal(@RequestBody ArchivesSeal archivesSeal) {
        archivesSeal.setId(StringUtil.getUuid());
        if(archivesSeal!=null){
            boolean bool=archivesSealService.addArchivesSeal(archivesSeal);
            if(bool){
                return new ResponseResult(ResponseResult.OK, "添加成功", true);
            }else{
                return new ResponseResult(ResponseResult.OK, "添加失败", false);
            }
        }else{
            return new ResponseResult(ResponseResult.OK, "参数传递错误", false);
        }
    }
}
