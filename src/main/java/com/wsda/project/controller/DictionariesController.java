package com.wsda.project.controller;

import com.wsda.project.model.ResponseResult;
import com.wsda.project.service.impl.ClassTreeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@Api("字典")
public class DictionariesController {
    private Logger logger = LoggerFactory.getLogger(DictionariesController.class);
    @Resource
    private ClassTreeServiceImpl classTreeService;

    @ApiOperation(value = "获取字典", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getAllDictionaryData", method = RequestMethod.GET)
    public ResponseResult getAllDictionaryData() {
        List<Map<String,String>> list=null;
        try {
            list=classTreeService.getAllDictionaryData();
            return  new ResponseResult(ResponseResult.OK, "成功", list,true);
        }catch (Exception e){
            return new ResponseResult(ResponseResult.OK, "获取字典异常--"+e.getMessage(),false);
        }
    }


    @ApiOperation(value = "测试", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ResponseResult test( @ApiParam(required =true, name = "parentCode", value = "父编号")String parentCode) {
        String filename = parentCode.substring(0, parentCode.lastIndexOf("."));
        System.err.println(filename);
        return new ResponseResult(ResponseResult.OK,"截取文件名称" +filename);
    }
}
