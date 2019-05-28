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

    @ApiOperation(value = "获取视图节点", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getTableView", method = RequestMethod.POST)
    public ResponseResult getTableView(@ApiParam(required =true, name = "tableCode", value = "表编号")String tableCode) {
        if (tableCode != null) {
            List<Map<String, Object>> parms = tableViewService.getTableView(tableCode);
            if (parms != null && parms.size() > 0) {
                return new ResponseResult(ResponseResult.OK, "查询成功 ", parms, true);
            } else {
                return new ResponseResult(ResponseResult.OK, "查询失败,该参数无字段列 ", "参数-" + tableCode, false);
            }
        } else {
            return new ResponseResult(ResponseResult.OK, "失败，参数无效 ", "参数-" + tableCode, false);
        }
    }


    @ApiOperation(value = "获取视图树", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getTableViewTree", method = RequestMethod.POST)
    public ResponseResult getTableViewTree() {
        Tree tree=tableViewService.getTreeMenu();
        return new ResponseResult(ResponseResult.OK, "成功", tree, true);
    }





}
