package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.clienUser;
import com.xh.b20220105backend.entity.clienUserExample;
import com.xh.b20220105backend.entity.request.passwordData;
import com.xh.b20220105backend.entity.request.userMainInfo;
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
            info.put("userName", clienUsers.get(0).getUsername());
            info.put("userDescribe", clienUsers.get(0).getUserdescribe());
            info.put("userIcon", clienUsers.get(0).getUsericon());
            String token = JwtUtil.createToken(info);
            return resultMapUtil.backResultMap("登录成功", token, 200);
        } catch (loginException e) {
            log.warn(e.getMessage());
            return resultMapUtil.backResultMap(e.getMessage(), "", -200);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return resultMapUtil.backResultMap(e.getMessage(), "", -201);
        }
    }

    @Override
    public clienUser getBaseUserInfo(String token) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        String userName = JwtUtil.getTokenStringInfo(token, "userName");
        String userDescribe = JwtUtil.getTokenStringInfo(token, "userDescribe");
        String userIcon = JwtUtil.getTokenStringInfo(token, "userIcon");
        clienUser clienUser = new clienUser();
        clienUser.setUserid(userId);
        clienUser.setUsername(userName);
        clienUser.setUserdescribe(userDescribe);
        clienUser.setUsericon(userIcon);

        return clienUser;
    }

    @Override
    public clienUser getMainUserInfo(String token) {
        clienUser clienUser = selectAllUserInfo(token);
        clienUser.setUserpassword(null);
        clienUser.setBegaintime(null);
        clienUser.setPacketid(null);
        clienUser.setUsertel(null);
        clienUser.setId(null);
        clienUser.setUserid(null);
        return clienUser;
    }


    @Override
    public clienUser selectAllUserInfo(String token) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        clienUserExample clienExample = new clienUserExample();
        clienExample.createCriteria().andUseridEqualTo(userId);
        List<clienUser> clienUsers = clienUserMapper.selectByExample(clienExample);
        if (!clienUsers.isEmpty()) {
            return clienUsers.get(0);
        } else {
            log.warn("selectAllUserInfo：查询用户为空");
            return null;
        }
    }

    @Override
    public Integer changeMainUserInfo(String token, userMainInfo usrMainInfo) {
        clienUser clienUser = selectAllUserInfo(token);
        if (clienUser!=null) {
            clienUser.setUserrealname(usrMainInfo.getRealName());
            clienUser.setUsername(usrMainInfo.getUserName());
            clienUser.setSex(usrMainInfo.getSex());
            clienUser.setBirthday(usrMainInfo.getBirthday());
            clienUser.setUserdescribe(usrMainInfo.getDescribe());
            return clienUserMapper.updateByPrimaryKey(clienUser);

        } else {
            log.warn("changeMainUserInfo：查询用户为空");
            return -1;
        }
    }

    @Override
    public Integer changeUserPassword(String token, passwordData passwordData) {
        clienUser clienUser = selectAllUserInfo(token);
        if (clienUser==null) {
            log.warn("changeUserPassword：查询用户为空");
            return -3;
        }
        String userpassword = clienUser.getUserpassword();
        if (!userpassword.equals(passwordData.getMetaPsw())) {
            return -1;
        }
        if (passwordData.getNewPsw().length() < 6) {
            return -2;
        }
        clienUser.setUserpassword(passwordData.getNewPsw());
        return clienUserMapper.updateByPrimaryKey(clienUser);
    }

}
