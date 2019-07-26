package com.wsda.project.service;

import com.wsda.project.model.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SystemUserService {
    ResponseResult getLoginIsOk(String userCode, String password, HttpServletRequest request,HttpServletResponse response);
    boolean getLoginOut(String userCode);

    boolean upThemeColorByUserCode(String userCode, String themeColor);
}
