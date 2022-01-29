package com.xh.b20220105backend.service;

import com.xh.b20220105backend.entity.response.resultMap;

public interface clienUserService {
    resultMap loginByPassword(String userId, String userPassword);
}
