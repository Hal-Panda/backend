package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.clienUser;
import com.xh.b20220105backend.entity.clienUserExample;
import com.xh.b20220105backend.entity.response.resultMap;
import com.xh.b20220105backend.exception.loginException;
import com.xh.b20220105backend.mapper.clienUserMapper;
import com.xh.b20220105backend.service.clienUserService;
import com.xh.b20220105backend.util.JwtUtil;
import com.xh.b20220105backend.util.resultMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class clienUserServiceImpl implements clienUserService {
    private final Integer duringTime = 600;

    @Resource
    private clienUserMapper clienUserMapper;


    @Override
    public resultMap loginByPassword(String userId, String userPassword) {
        clienUserExample clienUserExample = new clienUserExample();
        clienUserExample.createCriteria().andUseridEqualTo(userId);
        List<clienUser> clienUsers = clienUserMapper.selectByExample(clienUserExample);

        try {
            if (clienUsers.isEmpty()) {
                throw new loginException("用户名不正确");
            }
            if (!clienUsers.get(0).getUserpassword().equals(userPassword)) {
                throw new loginException("用户名或者密码错误");
            }
            HashMap info = new HashMap();
            info.put("userId", userId);
            info.put("loginTime", new SimpleDateFormat("yyyy-MM-dd@HH:mm:ss").format(new Date()));
            String token = JwtUtil.createToken(duringTime, info);
            return resultMapUtil.backResultMap("登录成功", token, 200);
        } catch (loginException e) {
            log.warn(e.getMessage());
            return resultMapUtil.backResultMap(e.getMessage(), "", -200);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return resultMapUtil.backResultMap(e.getMessage(), "", -201);
        }
    }
}
