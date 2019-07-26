package com.wsda.project.controller;

import com.wsda.project.model.ResponseResult;
import com.wsda.project.model.Tree;
import com.wsda.project.service.impl.DepartementServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value="部门",tags={"部门Controller"})
public class DepartementController {
    private Logger logger = LoggerFactory.getLogger(DepartementController.class);
    @Resource
    private DepartementServiceImpl departementService;
    @ApiOperation(value = "获取部门树", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/getDepartementTree", method = RequestMethod.GET)
    public ResponseResult getDepartementTree() {
        List<Tree> Dtree =departementService.getDepartementByParentCode();
        if(Dtree!=null){
            return new ResponseResult(ResponseResult.OK, "获取部门树成功", Dtree, true);
        }else{
            return new ResponseResult(ResponseResult.OK, "获取部门树失败", Dtree, false);
        }
    }
}
