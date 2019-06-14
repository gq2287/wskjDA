package com.wsda.project.service;

import com.wsda.project.model.ResponseResult;

import javax.servlet.http.HttpServletRequest;

public interface SystemUserService {
    ResponseResult getLoginIsOk(String userCode, String password, HttpServletRequest request);
    boolean getLoginOut(String userCode);
}
