package com.xh.b20220105backend.service;

import com.xh.b20220105backend.entity.packetInfo;
import com.xh.b20220105backend.entity.request.passwordData;

import java.math.BigDecimal;

public interface packetInfoService {
    Integer checkPswOfClien(String token,String password);

    Integer reduceMoney(String token, BigDecimal reduceMoney);

    Integer changePacketPassword(String token, passwordData passwordData);

    packetInfo selectPacketAllInfoByPid(Integer packetId);

    BigDecimal checkMoney(String token);

    Integer checkPassword(String token,String psw);
}
