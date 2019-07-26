package com.wsda.project.controller;

import com.wsda.project.model.FilingScopeTree;
import com.wsda.project.model.ResponseResult;
import com.wsda.project.service.impl.FilingScopeTreeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value="归档范围",tags={"归档范围Controller"})
public class FilingScopeTreeController {
    private Logger logger = LoggerFactory.getLogger(FilingScopeTreeController.class);
    @Resource
    private FilingScopeTreeServiceImpl filingScopeTreeService;

    @ApiOperation(value = "获取归档范围菜单", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getFilingScopeTreeMenu", method = RequestMethod.POST)
    public ResponseResult getFilingScopeTreeMenu() {
        List<FilingScopeTree> treeList=filingScopeTreeService.getFilingScopeTreeMenu();

        return new ResponseResult(ResponseResult.OK, "获取归档范围菜单",treeList, true);
    }

    @ApiOperation(value = "根据条件查询归档范围菜单", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getFilingScopeTreeByParms", method = RequestMethod.POST)
    public ResponseResult getFilingScopeTreeByParms(@RequestBody FilingScopeTree filingScopeTree) {
        List<FilingScopeTree> treeList=filingScopeTreeService.getFilingScopeTreeByParms(filingScopeTree);
        return new ResponseResult(ResponseResult.OK, "获取归档范围菜单",treeList, true);
    }

    @ApiOperation(value = "添加归档范围节点", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/addFilingScope", method = RequestMethod.POST)
    public ResponseResult addFilingScope(@RequestBody FilingScopeTree filingScopeTree) {
        return new ResponseResult(ResponseResult.OK, "添加保管期限节点成功", filingScopeTreeService.addFilingScope(filingScopeTree));
    }

    @ApiOperation(value = "删除归档范围节点", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/delFilingScopeByNodeCode", method = RequestMethod.POST)
    public ResponseResult delFilingScopeByNodeCode(@ApiParam(required = true, name = "nodeCode", value = "被删除节点编号") @RequestParam(name = "nodeCode", required = true) String nodeCode) {

        return new ResponseResult(ResponseResult.OK, "删除成功",filingScopeTreeService.delFilingScopeByNodeCode(nodeCode));
    }

    @ApiOperation(value = "获取最大的编号", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getFilingScopeMaxNodeCode", method = RequestMethod.POST)
    public ResponseResult getFilingScopeMaxNodeCode(@ApiParam(required = true, name = "parentCode", value = "被删除节点编号") @RequestParam(name = "parentCode", required = true) String parentCode) {
        return new ResponseResult(ResponseResult.OK, "删除成功",filingScopeTreeService.getFilingScopeMaxNodeCode(parentCode), true);
    }

    @ApiOperation(value = "修改归档范围", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/upFilingScopeNodeCode", method = RequestMethod.POST)
    public ResponseResult upFilingScopeNodeCode(@ApiParam(required = true, name = "nodeCode", value = "当前修改的nodeCode") @RequestParam(name = "nodeCode", required = true) String nodeCode,
                                                @ApiParam(required = true, name = "title", value = "标题") @RequestParam(name = "title", required = true) String title,
                                                @ApiParam(required = true, name = "dateOfCustody", value = "保管期限") @RequestParam(name = "dateOfCustody", required = true) String dateOfCustody) {
        return new ResponseResult(ResponseResult.OK, "修改成功",filingScopeTreeService.upFilingScopeNodeCode(nodeCode,title,dateOfCustody), true);
    }
}
