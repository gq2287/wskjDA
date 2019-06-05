package com.wsda.project.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.reflect.TypeToken;
import com.wsda.project.model.ResponseResult;
import com.wsda.project.model.Tree;
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

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("表视图Controller")
public class TableViewTreeController {
    private Logger logger = LoggerFactory.getLogger(TableViewTreeController.class);
    @Resource
    private TableViewServiceImpl tableViewService;

    @ApiOperation(value = "获取实体表数据", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getTableView", method = RequestMethod.POST)
    public ResponseResult getTableView(@ApiParam(required = true, name = "tableCode", value = "表编号") @RequestParam(name = "tableCode", required = true) String tableCode,
                                       @ApiParam(required = true, name = "pageNum", value = "当前页") @RequestParam(name = "pageNum", required = true) int pageNum,
                                       @ApiParam(required = true, name = "pageSize", value = "每页条目数") @RequestParam(name = "pageSize", required = true) int pageSize,
                                       @ApiParam(required = false, name = "conditions", value = "查询条件") @RequestParam(name = "conditions", required = false) String conditions,
                                       @ApiParam(required = false, name = "sorts", value = "排序条件") @RequestParam(name = "sorts", required = false) String sorts,
                                       @ApiParam(required = true, name = "type", value = "回收站标志（0不是回收站,1是回收站）") @RequestParam(name = "type", required = true) String type) {
        System.err.println(conditions+"\n********\n"+sorts+"********\n");
        List<Map<String, String>> conditionsMap = new ArrayList<>();
        List<Map<String, String>> sortsMap = new ArrayList<>();
        Type typeObj = new TypeToken<List<Map<String, String>>>() {
        }.getType();
        if (conditions != null) {
            conditionsMap = JSONObject.parseObject(conditions, typeObj);//JSONObject转换map
        }
        if (sorts != null) {
            sortsMap = JSONObject.parseObject(sorts, typeObj);//JSONObject转换map
        }
        if (tableCode != null) {
            Map<String, Object> parms = tableViewService.getTableView(tableCode, pageNum, pageSize, conditionsMap, sortsMap,type);
            if (parms != null) {
                return new ResponseResult(ResponseResult.OK, "查询成功 ", parms, true);
            } else {
                return new ResponseResult(ResponseResult.OK, "查询失败,该参数无字段列 ", "参数-" + tableCode, false);
            }
        } else {
            return new ResponseResult(ResponseResult.OK, "失败，参数无效 ", "参数-" + tableCode, false);
        }
    }


    @ApiOperation(value = "获取视图树", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getTableViewTree", method = RequestMethod.GET)
    public ResponseResult getTableViewTree() {
        Tree tree = tableViewService.getTreeMenu();
        return new ResponseResult(ResponseResult.OK, "成功", tree, true);
    }


    @ApiOperation(value = "获取录入数据", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getInputCard", method = RequestMethod.POST)
    public ResponseResult getInputCard(@ApiParam(required = true, name = "tableCode", value = "表编号") @RequestParam(name = "tableCode", required = true) String tableCode) {
        List<Map<String, Object>> inputCard = tableViewService.getInputCard(tableCode);
        return new ResponseResult(ResponseResult.OK, "成功", inputCard, true);
    }

    @ApiOperation(value = "添加档案条目", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/addTableInfo", method = RequestMethod.POST)
    public ResponseResult addTableInfo(@ApiParam(required = true, name = "tableCode", value = "表编号") @RequestParam(name = "tableCode", required = true) String tableCode,
                                       @ApiParam(required = true, name = "tableInfo", value = "档案对象数据") @RequestParam(name = "tableInfo", required = true) String tableInfo) {
        Map<String, String> tableInfoMap  = new HashMap<>();
        Type typeObj = new TypeToken<Map<String, String>>() {}.getType();
        if (tableInfo != null) {
            tableInfoMap = JSONObject.parseObject(String.valueOf(tableInfo), typeObj);//JSONObject转换map
        }
        tableInfoMap.put("tableCode",tableCode);
        boolean bool = tableViewService.addTableInfo(tableInfoMap);
        if(bool){
            return new ResponseResult(ResponseResult.OK, "成功", bool, true);
        }else {
            return new ResponseResult(ResponseResult.OK, "失败", bool, false);
        }
    }


    @ApiOperation(value = "获取全宗", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getAllSystemFonds", method = RequestMethod.POST)
    public ResponseResult getAllSystemFonds() {
        List<Map<String, String>> systemFonds = tableViewService.getAllSystemFonds();
        return new ResponseResult(ResponseResult.OK, "成功", systemFonds, true);
    }

    @ApiOperation(value = "获取实体分类", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getAllSystemFondsTree", method = RequestMethod.POST)
    public ResponseResult getAllSystemFondsTree(@RequestParam(name = "fondsCode", required = true) String fondsCode) {
        List<Tree> systemFonds = tableViewService.getAllSystemFondsTree(fondsCode);
        if(systemFonds!=null){
            return new ResponseResult(ResponseResult.OK, "成功", systemFonds, true);
        }else{
            return new ResponseResult(ResponseResult.OK, "成功", systemFonds, false);
        }
    }

    @ApiOperation(value = "分组", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getGroup", method = RequestMethod.POST)
    public ResponseResult getGroup(@ApiParam(required = true, name = "tableCode", value = "表编号")@RequestParam(name = "tableCode", required = true) String tableCode,
                                   @ApiParam(required = true, name = "group", value = "分组列 字段英文名") @RequestParam(name = "group", required = true) String group) {
        List<String> groupList = tableViewService.getGroup(tableCode,group);
        if(groupList!=null){
            return new ResponseResult(ResponseResult.OK, "成功", groupList, true);
        }else{
            return new ResponseResult(ResponseResult.OK, "成功", groupList, false);
        }
    }

    @ApiOperation(value = "恢复删除档案条目", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/upArchives", method = RequestMethod.POST)
    public ResponseResult upArchives(@ApiParam(required = true, name = "tableCode", value = "档案表编号") @RequestParam(name = "tableCode", required = true) String tableCode,
                                     @ApiParam(required = true, name = "recordCode", value = "档案主键") @RequestParam(name = "recordCode", required = true) String[] recordCode,
                                     @ApiParam(required = true, name = "trashStatus", value = "回收站(0恢复,1放入回收站)") @RequestParam(name = "trashStatus", required = true) String trashStatus) {
        boolean result=true;
        try {
            result=tableViewService.upArchives(tableCode,recordCode,trashStatus);
        }catch (Exception e){
            result=false;
        }
        if(result){//不能真正删除档案，只做状态码的改变放入回收站即可
            return new ResponseResult(ResponseResult.OK, "成功", result, true);
        }else{
            return new ResponseResult(ResponseResult.OK, "失败", result, false);
        }
    }

    @ApiOperation(value = "获取当前档案条目详情", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getArchives", method = RequestMethod.POST)
    public ResponseResult getArchives(@ApiParam(required = true, name = "tableCode", value = "档案表编号") @RequestParam(name = "tableCode", required = true) String tableCode,
                                     @ApiParam(required = true, name = "recordCode", value = "档案主键") @RequestParam(name = "recordCode", required = true) String recordCode) {
        Map<String,String> result=tableViewService.getArchives(tableCode,recordCode);
        if(result!=null&&result.size()>0){
            return new ResponseResult(ResponseResult.OK, "成功", result, true);
        }else{
            return new ResponseResult(ResponseResult.OK, "失败", result, false);
        }
    }


    @ApiOperation(value = "修改档案条目", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/upArchivesByRecordCode", method = RequestMethod.POST)
    public ResponseResult upArchivesByRecordCode(@ApiParam(required = true, name = "tableCode", value = "档案表编号") @RequestParam(name = "tableCode", required = true) String tableCode,
                                                 @ApiParam(required = true, name = "recordCode", value = "档案主键") @RequestParam(name = "recordCode", required = true) String recordCode,
                                                 @ApiParam(required = true, name = "parms", value = "修改列详情") @RequestParam(name = "parms", required = true) String parms) {
        Map<String, String> parmsMap = new HashMap<>();
        Type typeObj = new TypeToken<Map<String, String>>() {}.getType();
        if (parms != null) {
            parmsMap = JSONObject.parseObject(parms, typeObj);//JSONObject转换map
        }
        boolean result=tableViewService.upArchivesByRecordCode(tableCode,recordCode,parmsMap);
        if(result){
            return new ResponseResult(ResponseResult.OK, "成功", result, true);
        }else{
            return new ResponseResult(ResponseResult.OK, "失败", result, false);
        }
    }


}
