package com.wsda.project.service.impl;

import com.wsda.project.dao.SystemUserMapper;
import com.wsda.project.model.ResponseResult;
import com.wsda.project.model.SystemUser;
import com.wsda.project.service.SystemUserService;
import com.wsda.project.util.HttpUtils;
import com.wsda.project.util.Md5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class SystemUserServiceImpl implements SystemUserService {
    @Resource
    private SystemUserMapper userMapper;

    /**
     * 登录
     *
     * @param userCode
     * @param password
     * @param request  登录ip
     * @return
     */
    @Override
    public ResponseResult getLoginIsOk(String userCode, String password, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        ResponseResult responseResult = null;
        if (session.getAttribute("user") == null) {//判断session是否存在
            SystemUser user = userMapper.getUserInfo(userCode);
            if (user != null) {
                password = password + "wsda";//注册和添加都要    wsda是私匙 （用户设置的密码+wsda MD5加密后存入数据库对比）
                password = Md5Utils.MD5Encode(password, "utf-8", false);
                if (password.equals(user.getPassword())) {
                    String ip = HttpUtils.getRealIp(request);
                    user.setUrl(ip);
                    boolean bool = userMapper.upSystemUserStateAndIpByUserCode(userCode, 1 + "", ip,session.getId());
                    if (bool) {
                        System.out.println("登录状态修改成功");
                    }
                    user = userMapper.getUserInfo(userCode);
                    session.setAttribute("user", user);//把用户放入session
                    responseResult = new ResponseResult(ResponseResult.OK, "登录成功",user, true);
                    //把token 放入cokie
                    Cookie cookie  =  new Cookie("token",user.getToken());
                    //设置cookie的生命周期
//                    cookie.setMaxAge(0);//不记录cookie
                    cookie.setMaxAge(-1);//会话级cookie，关闭浏览器失效
//                    cookie.setMaxAge(60*60);//过期时间为1小时
                    //向客户端增加cookie对象
                    response.addCookie(cookie);

                } else {
                    responseResult = new ResponseResult(ResponseResult.OK, "密码错误,请检查(字母注意大小写)", false);
                }
            } else {
                responseResult = new ResponseResult(ResponseResult.OK, "账号不存在,请检查", false);
            }
        } else {
            SystemUser user= (SystemUser) session.getAttribute("user");
            responseResult = new ResponseResult(ResponseResult.OK, "登录成功",user, true);
        }
        return responseResult;
    }

    /**
     * 退出登录
     *
     * @param userCode
     * @return
     */
    @Override
    public boolean getLoginOut(String userCode) {
        boolean bool = userMapper.upSystemUserStateAndIpByUserCode(userCode, 0 + "", 0 + "",null);
        return bool;
    }

    @Override
    public boolean upThemeColorByUserCode(String userCode, String themeColor) {
        return userMapper.upThemeColorByUserCode(userCode, themeColor);
    }


}


