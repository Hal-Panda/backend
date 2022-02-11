package com.xh.b20220105backend.service;

import java.math.BigDecimal;

public interface packetInfoService {
    Integer checkPswOfClien(String token,String password);

    Integer reduceMoney(String token, BigDecimal reduceMoney);
}
