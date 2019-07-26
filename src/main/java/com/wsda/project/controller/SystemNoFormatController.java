package com.wsda.project.controller;

import com.wsda.project.model.ResponseResult;
import com.wsda.project.model.SystemNoFormat;
import com.wsda.project.service.impl.SystemNoFormatServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "档号格式设置",tags = {"档号格式设置Controller"})
public class SystemNoFormatController {
    private Logger logger = LoggerFactory.getLogger(SystemNoFormatController.class);
    @Resource
    private SystemNoFormatServiceImpl systemNoFormatService;

    @ApiOperation(value = "获取档号列表", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getSystemNoFormatList", method = RequestMethod.POST)
    public ResponseResult getSystemNoFormatList(){
        return new ResponseResult(ResponseResult.OK, "获取档号列表",systemNoFormatService.getSystemNoFormatList(), true);
    }

    @ApiOperation(value = "获取指定底层门类档号列表", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getSystemNoFormatListByEntityCode", method = RequestMethod.POST)
    public ResponseResult  getSystemNoFormatListByEntityCode(String entityCode){
        return  new ResponseResult(ResponseResult.OK, "获取档号列表",systemNoFormatService.getSystemNoFormatListByEntityCode(entityCode), true);
    }

    @ApiOperation(value = "获取设置档号项列表", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getSystemNoFormatColumnsByEntityCode", method = RequestMethod.POST)
    public ResponseResult  getSystemNoFormatColumnsByEntityCode(String entityCode){
        List<Map<String,String>> result=systemNoFormatService.getSystemNoFormatColumnsByEntityCode(entityCode);
        if(result!=null){
            return  new ResponseResult(ResponseResult.OK, "获取档号设置成功",result, true);
        }
        return  new ResponseResult(ResponseResult.OK, "系统内部错误", false);
    }


    @ApiOperation(value = "添加档号设置", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/addSystemNoFormat", method = RequestMethod.POST)
    public ResponseResult  addSystemNoFormat(@RequestBody List<SystemNoFormat> systemNoFormat){
        int result=systemNoFormatService.addSystemNoFormat(systemNoFormat);
        if(result>0){
            return  new ResponseResult(ResponseResult.OK, "添加档号成功", true);
        }
        return  new ResponseResult(ResponseResult.OK, "添加失败", false);
    }

}
