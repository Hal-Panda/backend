//package com.xh.b20220105backend.service.impl;
//
//import com.xh.b20220105backend.entity.response.resultMap;
//import com.xh.b20220105backend.service.xuserService;
//import com.xh.b20220105backend.util.JwtUtil;
//import com.xh.b20220105backend.util.resultMapUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//@Service
//@Slf4j
//public class xuserServiceImpl implements xuserService {
//    @Resource
//    private xuserMapper xuserMapper;
//
//    @Override
//    public resultMap xloginByPassword(String userNum, String password) {
//        xuserExample xuserExample = new xuserExample();
//        xuserExample.createCriteria().andUsernumEqualTo(userNum);
//        List<xuser> xusers = xuserMapper.selectByExample(xuserExample);
//        try {
//            if (xusers.isEmpty()) {
//                throw new Exception("用户不存在");
//            }
//            if (!xusers.get(0).getPassword().equals(password)) {
//                throw new Exception("密码错误");
//            }
//            Map info = new HashMap();
//            info.put("userNum", userNum);
//            info.put("password", password);
//            String token = JwtUtil.createToken(600, info);
//            return resultMapUtil.backResultMap("登陆成功",token,100);
//        } catch (Exception e) {
//            log.warn(e.getMessage());
//            return resultMapUtil.backResultMap("密码或账号错误","",-100);
//        }
//    }
//}
