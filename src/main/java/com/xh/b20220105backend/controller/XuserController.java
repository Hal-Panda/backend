//package com.xh.b20220105backend.controller;
//
//import com.xh.b20220105backend.entity.response.resultMap;
//import com.xh.b20220105backend.service.xuserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/user")
//public class XuserController {
//    @Autowired
//    private xuserService xuserService;
//
//    @RequestMapping("/open/xLoginP")
//    public resultMap loginByPassword(String userNum, String password){
//        resultMap resultMap = xuserService.xloginByPassword(userNum, password);
//        return resultMap;
//    }
//
//}
