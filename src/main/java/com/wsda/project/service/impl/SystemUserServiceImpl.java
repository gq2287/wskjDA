package com.wsda.project.service.impl;

import com.wsda.project.dao.SystemUserMapper;
import com.wsda.project.model.ResponseResult;
import com.wsda.project.model.SystemUser;
import com.wsda.project.service.SystemUserService;
import com.wsda.project.util.HttpUtils;
import com.wsda.project.util.Md5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public ResponseResult getLoginIsOk(String userCode, String password, HttpServletRequest request) {
        HttpSession session=request.getSession();
        ResponseResult responseResult = null;
        if(session.getAttribute("user")==null){
            SystemUser user = userMapper.getUserInfo(userCode);
            if (user != null) {
                password = password + "wsda";//注册和添加都要    wsda是公钥
                password = Md5Utils.MD5Encode(password, "utf-8", false);
                if (password.equals(user.getPassword())) {
                    String ip = HttpUtils.getRealIp(request);
                    if ("0".equals(user.getActiveState())&&"0".equals(user.getUrl())) {
                        user.setUrl(ip);
                        boolean bool = userMapper.upSystemUserStateAndIpByUserCode(userCode, 1 + "",ip);
                        if (bool) {
                            System.out.println("登录状态修改成功");
                        }
                        user = userMapper.getUserInfo(userCode);
                        session.setAttribute("user",user);//把用户放入session
                        responseResult = new ResponseResult(ResponseResult.OK, "登录成功", user, true);
                    } else {
                        responseResult = new ResponseResult(ResponseResult.OK, "该用户已在【" + user.getUrl() + "】地址登录", false);
                    }
                } else {
                    responseResult = new ResponseResult(ResponseResult.OK, "密码错误,请检查(字母注意大小写)", false);
                }
            } else {
                responseResult = new ResponseResult(ResponseResult.OK, "账号不存在,请检查", false);
            }
        }else{
            responseResult = new ResponseResult(ResponseResult.OK, "登录成功", session.getAttribute("user"), true);
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
        boolean bool = userMapper.upSystemUserStateAndIpByUserCode(userCode, 0 + "",0+"");
        return bool;
    }


}
