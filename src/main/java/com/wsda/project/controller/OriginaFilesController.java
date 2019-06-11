package com.wsda.project.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.reflect.TypeToken;
import com.wsda.project.model.ResponseResult;
import com.wsda.project.service.impl.OriginaFilesServiceImpl;
import com.wsda.project.service.impl.TableViewServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("原文Controller")
public class OriginaFilesController {
    private Logger logger = LoggerFactory.getLogger(OriginaFilesController.class);
    @Resource
    private OriginaFilesServiceImpl originaFilesService;
    @Resource
    private TableViewServiceImpl tableViewService;
    @ApiOperation(value = "获取档案下原文信息", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getOriginaFileSByRecordCode", method = RequestMethod.POST)
    public ResponseResult getOriginaFileSByRecordCode(@ApiParam(required = true, name = "tableCode", value = "表编号")@RequestParam(name = "tableCode", required = false,defaultValue = "187530")String tableCode,
                                                      @ApiParam(required = true, name = "recordCode", value = "档案唯一编号")@RequestParam(name = "recordCode", required = true)String recordCode,
                                                      @ApiParam(required = false, name = "pageNum", value = "当前页") @RequestParam(name = "pageNum", required = true)Integer pageNum,
                                                      @ApiParam(required = false, name = "pageSize", value = "每页条目数")@RequestParam(name = "pageSize", required = true)Integer pageSize) {
        if(recordCode!=null&&pageNum!=null&&pageSize!=null){
            Map<String,Object> pageFileDate=originaFilesService.getFilesByRecordCode(tableCode,recordCode,pageNum,pageSize);
            if(pageFileDate.get("originaFilePageInfo")!=null){
                return new ResponseResult(ResponseResult.OK, "获取原文信息成功", pageFileDate, true);
            }else{
                return new ResponseResult(ResponseResult.OK, "当前档案暂无原文可供查看", pageFileDate, true);
            }

        }else{
            return new ResponseResult(ResponseResult.OK, "参数查询不完整", null, false);
        }
    }

    @ApiOperation(value = "修改原文信息", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/upOriginaFilesByFileCode", method = RequestMethod.POST)
    public ResponseResult upOriginaFilesByFileCode(@ApiParam(required = true, name = "tableCode", value = "档案表编号") @RequestParam(name = "tableCode",  required = false,defaultValue = "187530") String tableCode,
                                                 @ApiParam(required = true, name = "fileCode", value = "原文主键") @RequestParam(name = "fileCode", required = true) String fileCode,
                                                 @ApiParam(required = true, name = "params", value = "修改列详情") @RequestParam(name = "params", required = true) String params) {
        Map<String, String> paramsMap = new HashMap<>();
        Type typeObj = new TypeToken<Map<String, String>>() {}.getType();
        if (params != null) {
            paramsMap = JSONObject.parseObject(params, typeObj);//JSONObject转换map
        }
        boolean result=tableViewService.upArchivesByRecordCode(tableCode,fileCode,paramsMap,1);
        if(result){
            return new ResponseResult(ResponseResult.OK, "成功", result, true);
        }else{
            return new ResponseResult(ResponseResult.OK, "失败", result, false);
        }
    }

    @ApiOperation(value = "恢复删除原文信息", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/upOriginaFilesArchives", method = RequestMethod.POST)
    public ResponseResult upOriginaFilesArchives(@ApiParam(required = true, name = "tableCode", value = "档案表编号") @RequestParam(name = "tableCode", required = false,defaultValue = "187530") String tableCode,
                                     @ApiParam(required = true, name = "fileCode", value = "原文主键") @RequestParam(name = "fileCode",required = true)  String fileCode,
                                     @ApiParam(required = true, name = "trashStatus", value = "回收站(0恢复,1放入回收站)") @RequestParam(name = "trashStatus", required = true) String trashStatus) {
        boolean result=true;
        try {
            Type typeObj = new TypeToken<List<String>>() {}.getType();
            List<String> recordCodeList= JSONObject.parseObject(fileCode, typeObj);//JSONObject转换map
            result=tableViewService.upArchives(tableCode,recordCodeList,trashStatus,1);
        }catch (Exception e){
            result=false;
        }
        if(result){//不能真正删除档案，只做状态码的改变放入回收站即可
            return new ResponseResult(ResponseResult.OK, "成功", result, true);
        }else{
            return new ResponseResult(ResponseResult.OK, "失败", result, false);
        }
    }


    @ApiOperation(value = "获取当前文件条目详情", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getOriginaFilesArchives", method = RequestMethod.POST)
    public ResponseResult getOriginaFilesArchives(@ApiParam(required = true, name = "tableCode", value = "档案表编号") @RequestParam(name = "tableCode",  required = false,defaultValue = "187530") String tableCode,
                                      @ApiParam(required = true, name = "fileCode", value = "原文主键") @RequestParam(name = "fileCode", required = true) String fileCode) {
        Map<String,String> result=tableViewService.getArchives(tableCode,fileCode,1);
        if(result!=null&&result.size()>0){
            return new ResponseResult(ResponseResult.OK, "成功", result, true);
        }else{
            return new ResponseResult(ResponseResult.OK, "失败", result, false);
        }
    }

    @ApiOperation(value = "上传新档案原文", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/upLoadFiles", method = RequestMethod.POST)
    public ResponseResult upLoadFiles(@ApiParam(required = true, name = "tableCode", value = "档案表编号")@RequestParam(name = "tableCode", required = true)String tableCode,
                                      @ApiParam(required = true, name = "recordCode", value = "原文主键")@RequestParam(name = "recordCode", required = true)String recordCode,
                                      @ApiParam(required = true, name = "files", value = "上传文件")@RequestParam("files") MultipartFile files) {

        return new ResponseResult(ResponseResult.OK, "上传新档案原文", null, true);
    }
}
