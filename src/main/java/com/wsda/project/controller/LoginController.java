package com.wsda.project.controller;

import com.wsda.project.model.ResponseResult;
import com.wsda.project.model.SystemUser;
import com.wsda.project.service.impl.SystemUserServiceImpl;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录
 */

@RestController
@Api("登录Controller")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Resource
    private SystemUserServiceImpl systemUserService;

    @ApiOperation(value = "用户登录", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    public ResponseResult loginCheck(String userCode, String passWord, HttpServletRequest request, HttpServletResponse response) {
        if (userCode != null && !"".equals(userCode) && passWord != null && !"".equals(passWord)) {
            return systemUserService.getLoginIsOk(userCode, passWord, request,response);
        } else {
            return new ResponseResult(ResponseResult.OK, "账号或密码不能为空", false);
        }
    }


    @ApiOperation(value = "退出登录", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    public ResponseResult loginOut(String userCode, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");//销毁用户信息
        session.invalidate();//销毁session
        if (userCode!=null) {
            boolean bool = systemUserService.getLoginOut(userCode);
            if (bool) {
                return new ResponseResult(ResponseResult.OK, "退出成功", true);
            } else {
                return new ResponseResult(ResponseResult.OK, "退出失败", false);
            }
        } else {
            return new ResponseResult(ResponseResult.OK, "退出成功", true);
        }
    }

    @ApiOperation(value = "修改主题色", notes = "返回信息 0成功，400失败 ")
    @RequestMapping(value = "/upThemeColor", method = RequestMethod.POST)
    public ResponseResult upThemeColor(@ApiParam(required = true, name = "themeColor", value = "主题色") @RequestParam(name = "themeColor", required = true) String themeColor,
                                       HttpServletRequest request) {
        HttpSession session=request.getSession();
        SystemUser user= (SystemUser) session.getAttribute("user");
        if (user != null && themeColor !=null) {
            boolean bool = systemUserService.upThemeColorByUserCode(user.getUserCode(),themeColor);
            if (bool) {
                return new ResponseResult(ResponseResult.OK, "修改成功", true);
            } else {
                return new ResponseResult(ResponseResult.OK, "修改失败,参数异常", false);
            }
        } else {
            return new ResponseResult(ResponseResult.OK, "修改失败,账号已在其他地方登录", false);
        }
    }
}
