//package com.xh.b20220105backend.controller;
//
//
//import com.xh.b20220105backend.entity.goods;
//import com.xh.b20220105backend.entity.response.resultMap;
//import com.xh.b20220105backend.service.goodService;
//import com.xh.b20220105backend.util.resultMapUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.*;
//import java.util.List;
//
//@Slf4j
//@Controller
//public class testController {
//    @Autowired
//    private goodService goodService;
//
//    @ResponseBody
//    @RequestMapping("/test")
//    public resultMap showAll(){
//        List<goods> goods = goodService.showAll();
//        resultMap data = resultMapUtil.backResultMap("ok", goods, 100);
//        return data;
//    }
//
//    @ResponseBody
//    @RequestMapping("/toLogin")
//    public String login(HttpServletRequest request, HttpServletResponse response){
//        Cookie cookie=new Cookie("lwwwwwd","dwdadawdadwaa");
////        request.setAttribute("token33","02020202");
//        response.addCookie(cookie);
//        response.setHeader("Access-Control-Allow-Credentials","true");
////        response.setHeader("token","1232121111");
//        response.setHeader("Access-Control-Expose-Headers ", "Date");
//
//
////        String id = session.getId();
//
//        return "ok";
//    }
//
//    @ResponseBody
//    @RequestMapping("/showValue")
//    public String showValue(HttpServletRequest request, HttpServletResponse response,String id){
//        String token = request.getHeader("token4");
//        log.info("The test info is :{}", token);
//        System.out.println("-------"+token);
//        return "ua.toString()";
//    }
//
//    @ResponseBody
//    @RequestMapping("showGood/{goodid}")
//    public String showGood(@PathVariable String goodid){
//        System.out.println(goodid);
//        return goodid;
//    }
//
////    @GetMapping("noTest")
////    @ResponseBody
////    public String snotest(){
////        String id ="0";
////        if (id.isEmpty()){
////            id="2";
////        }
////        return id;
////    }
//}
