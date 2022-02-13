package com.xh.b20220105backend.service;

import com.xh.b20220105backend.entity.clienUser;
import com.xh.b20220105backend.entity.request.passwordData;
import com.xh.b20220105backend.entity.request.registerData;
import com.xh.b20220105backend.entity.request.userMainInfo;
import com.xh.b20220105backend.entity.response.resultMap;

public interface clienUserService {
    resultMap loginByPassword(String userId, String userPassword);

    clienUser getBaseUserInfo(String token);

    clienUser getMainUserInfo(String token);

    clienUser selectAllUserInfo(String token);

    clienUser selectAllUserInfoByUserId(String userId);

    Integer changeMainUserInfo(String token, userMainInfo userMainInfo);

    Integer changeUserPassword(String token, passwordData passwordData);

    Integer registerByAcount(registerData registerData,Integer packetId);

    Integer registerCheck(registerData registerData);


}
