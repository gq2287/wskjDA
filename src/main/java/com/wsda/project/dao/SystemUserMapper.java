package com.wsda.project.dao;

import com.wsda.project.model.SystemUser;
import org.apache.ibatis.annotations.Param;

public interface SystemUserMapper {
    SystemUser getUserInfo(@Param("userCode") String userCode);
    boolean upSystemUserStateAndIpByUserCode(@Param("userCode") String userCode,@Param("activeState") String activeState,@Param("ip") String ip,@Param("token") String token);
    boolean upThemeColorByUserCode(@Param("userCode") String userCode,@Param("themeColor") String themeColor);
    String getToken(@Param("userCode") String userCode);
}
