package com.xh.b20220105backend;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xh.b20220105backend.entity.goodOrder;
import com.xh.b20220105backend.entity.goodOrderExample;
import com.xh.b20220105backend.exception.loginException;
import com.xh.b20220105backend.mapper.goodOrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class B20220105backendApplicationTests {
    @Autowired
    private Integer showNum;

    @Test
    void contextLoads() {
        System.out.println(showNum);
        Calendar instance=Calendar.getInstance();
        instance.add(Calendar.SECOND,30);
        Map map=new HashMap();
        map.put("id","123213");
        map.put("tel","13995883");
//        String token= JWT.create()
//                .withClaim("id","d12324412")
//                .withClaim("1id","d12324412")
//                .withClaim("2id","d12324412")
//                .withClaim("3id",showNum)
//                .withExpiresAt(instance.getTime())
//                .sign(Algorithm.HMAC256("hahahs!123a"));
        JWTCreator.Builder builder = JWT.create();
        builder.withHeader(map);
        builder.withExpiresAt(instance.getTime());
        String token = builder.sign(Algorithm.HMAC256("asdasa"));

        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256("asdasa")).build();
        DecodedJWT decodedJWT=jwtVerifier.verify(token);

        System.out.println("map:"+ decodedJWT.getHeader());
//        System.out.println(token);
//        System.out.println(token.length());
    }


    @Test
    void userTest(){
        loginException loginException = new loginException("未登录");

        try {
            throw loginException;
        }
        catch (loginException e){

            System.out.println(new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()));
        }
    }

//    @Resource
//    private sellGoodsMapper sellGoodsMapper;
//
    @Resource
    private goodOrderMapper goodOrderMapper;
    @Test
    void pagerTest(){
        PageHelper.startPage(3, 1);
        List<goodOrder> goodOrders = goodOrderMapper.selectByExample(new goodOrderExample());
        PageInfo<goodOrder> PageInfo = new PageInfo<goodOrder>(goodOrders);
        System.out.println(PageInfo.getList().get(0).getId());
//

//        System.out.println(PageInfo);
//        PageHelper.startPage(1, 10);
//        List<User> list = userMapper.selectIf(1);
    }
}


