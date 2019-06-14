package com.wsda.project.controller;

import com.wsda.project.model.ResponseResult;
import com.wsda.project.service.impl.SystemUserServiceImpl;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *用户收藏
 */

@RestController
@Api("用户收藏Controller")
public class UserCollectionController {
    private Logger logger = LoggerFactory.getLogger(UserCollectionController.class);
    @Resource
    private SystemUserServiceImpl systemUserService;
//    @ApiOperation(value = "用户登录", notes = "返回信息 0成功，400失败 ")
//    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    public ResponseResult loginCheck(String userCode) {
        return new ResponseResult(ResponseResult.OK,"账号或密码不能为空",false);
    }
}
