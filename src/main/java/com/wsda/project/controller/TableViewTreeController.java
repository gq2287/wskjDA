package com.wsda.project.controller;

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
    public ResponseResult getTableView(@ApiParam(required =true, name = "tableCode", value = "表编号")@RequestParam(name = "tableCode",required = true)String tableCode,
    @ApiParam(required =true, name = "pageNum", value = "当前页")@RequestParam(name = "pageNum", required = true)int pageNum,
    @ApiParam(required =true, name = "pageSize", value = "每页条目数")@RequestParam(name = "pageSize", required = true)int pageSize) {
        if (tableCode != null) {
            Map<String,Object> parms = tableViewService.getTableView(tableCode,pageNum,pageSize);
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
        Tree tree=tableViewService.getTreeMenu();
        return new ResponseResult(ResponseResult.OK, "成功", tree, true);
    }


    @ApiOperation(value = "获取录入数据", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getInputCard", method = RequestMethod.POST)
    public ResponseResult getInputCard(@ApiParam(required =true, name = "tableCode", value = "表编号")@RequestParam(name = "tableCode",required = true)String tableCode) {
        List<Map<String,String>> inputCard=tableViewService.getInputCard(tableCode);
        return new ResponseResult(ResponseResult.OK, "成功", inputCard, true);
    }






}
