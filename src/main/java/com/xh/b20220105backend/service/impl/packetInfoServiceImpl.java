package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.clienUser;
import com.xh.b20220105backend.entity.clienUserExample;
import com.xh.b20220105backend.entity.packetInfo;
import com.xh.b20220105backend.entity.packetInfoExample;
import com.xh.b20220105backend.entity.request.passwordData;
import com.xh.b20220105backend.mapper.packetInfoMapper;
import com.xh.b20220105backend.service.clienUserService;
import com.xh.b20220105backend.service.packetInfoService;
import com.xh.b20220105backend.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class packetInfoServiceImpl implements packetInfoService {

    @Autowired
    private clienUserService clienUserService;
    @Resource
    private packetInfoMapper packetInfoMapper;

    @Override
    public Integer checkPswOfClien(String token, String password) {
        clienUser clienUser = clienUserService.selectAllUserInfo(token);
        if (clienUser==null){
            log.warn("checkPswOfClien:用户为空，clienUser==null");
            return 0;
        }
        Integer packetid = clienUser.getPacketid();
        packetInfoExample packetExample = new packetInfoExample();
        packetExample.createCriteria().andPacketidEqualTo(packetid);
        List<packetInfo> packetInfos = packetInfoMapper.selectByExample(packetExample);
        if (packetInfos.isEmpty()){
            log.warn("checkPswOfClien:没有对应钱包，packetInfos为空");
            return 0;
        }
        packetInfo packetInfo = packetInfos.get(0);

        String metaPsw = packetInfo.getPsw();
        if (metaPsw.equals(password)){
            return 1;
        }
        else {
            log.info("checkPswOfClien:支付密码错误");
            return -1;
        }
    }

    @Override
    public Integer reduceMoney(String token,BigDecimal reduceMoney) {
        clienUser clienUser = clienUserService.selectAllUserInfo(token);
        if (clienUser==null){
            log.warn("reduceMoney:用户为空，clienUser==null");
            return 0;
        }
        Integer packetid = clienUser.getPacketid();
        packetInfoExample packetExample = new packetInfoExample();
        packetExample.createCriteria().andPacketidEqualTo(packetid);
        List<packetInfo> packetInfos = packetInfoMapper.selectByExample(packetExample);
        if (packetInfos.isEmpty()){
            log.warn("reduceMoney:没有对应钱包，packetInfos为空");
            return 0;
        }
        packetInfo packetInfo = packetInfos.get(0);
        if (reduceMoney.compareTo(new BigDecimal(0))<0) {
            log.warn("reduceMoney:扣款金额不能为负数");
            return 0;
        }
        BigDecimal money = packetInfo.getMoney();
        int i = money.compareTo(reduceMoney);
        if (i<0){
            log.info("余额不足");
           return -1;
        }
        else {
            packetInfo.setMoney(money.subtract(reduceMoney));
            int flat = packetInfoMapper.updateByPrimaryKey(packetInfo);
            if (flat==1){
                return 1;
            }
            else {
                return -2;
            }
        }
    }

    @Override
    public Integer changePacketPassword(String token, passwordData passwordData) {
        clienUser clienUser = clienUserService.selectAllUserInfo(token);
        if (clienUser==null) {
            return -4;
        }
        Integer packetid = clienUser.getPacketid();
        packetInfo packetInfo = selectPacketAllInfoByPid(packetid);
        if (packetInfo==null){
            return -3;
        }
        if (!packetInfo.getPsw().equals(passwordData.getMetaPsw())){
            return -2;
        }
        if (passwordData.getNewPsw().length()!=6){
            return -1;
        }
        packetInfo.setPsw(passwordData.getNewPsw());
        return packetInfoMapper.updateByPrimaryKey(packetInfo);
    }

    @Override
    public packetInfo selectPacketAllInfoByPid(Integer packetId) {
        packetInfoExample packetInfoExample = new packetInfoExample();
        packetInfoExample.createCriteria().andPacketidEqualTo(packetId);
        List<packetInfo> packetInfos = packetInfoMapper.selectByExample(packetInfoExample);
        if (packetInfos.isEmpty()){
            log.warn("selectPacketAllInfoByPid：钱包为空");
            return null;
        }
        return packetInfos.get(0);
    }

    @Override
    public BigDecimal checkMoney(String token) {
        clienUser clienUser = clienUserService.selectAllUserInfo(token);
        if (clienUser==null){
            return null;
        }
        Integer packetid = clienUser.getPacketid();
        packetInfo packetInfo = selectPacketAllInfoByPid(packetid);
        if (packetInfo==null){
            return null;
        }
        return packetInfo.getMoney();
    }

    @Override
    public Integer checkPassword(String token,String psw) {
        clienUser clienUser = clienUserService.selectAllUserInfo(token);
        if (clienUser==null){
            return -3;
        }
        Integer packetid = clienUser.getPacketid();
        packetInfo packetInfo = selectPacketAllInfoByPid(packetid);
        if (packetInfo==null){
            return -2;
        }
        String metaPsw = packetInfo.getPsw();
        if (!metaPsw.equals(psw)){
            return -1;
        }
        return 1;
    }


}
