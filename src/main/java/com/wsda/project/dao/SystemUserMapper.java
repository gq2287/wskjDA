package com.wsda.project.dao;

import com.wsda.project.model.SystemUser;
import org.apache.ibatis.annotations.Param;

public interface SystemUserMapper {
    SystemUser getUserInfo(@Param("userCode") String userCode);
    boolean upSystemUserStateByUserCode(@Param("userCode") String userCode,@Param("activeState") String activeState);

}
