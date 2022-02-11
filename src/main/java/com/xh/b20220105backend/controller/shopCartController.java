package com.xh.b20220105backend.controller;

import com.xh.b20220105backend.entity.request.shopCartData;
import com.xh.b20220105backend.entity.response.resultMap;
import com.xh.b20220105backend.service.shopCartService;
import com.xh.b20220105backend.util.resultMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("shopCart")
public class shopCartController {
    @Autowired
    private shopCartService shopCartService;

    @PostMapping("addGood")
    public resultMap addGood(@RequestBody shopCartData shopCartData, HttpServletRequest request){
        String token = request.getHeader("token");
        Boolean flat = shopCartService.addShopCart(shopCartData, token);
        if (flat){
            return  resultMapUtil.backResultMap("添加成功", "1", 100);
        }
        else {
            return  resultMapUtil.backResultMap("添加失败", "0", 100);
        }
    }

    @RequestMapping("showCart")
    public resultMap showCart(HttpServletRequest request){
        String token = request.getHeader("token");
        List<shopCartData> shopCartData = shopCartService.showCart(token);
        return  resultMapUtil.backResultMap("查询成功", shopCartData, 100);
    }

    @RequestMapping("incCount")
    public void incCount(HttpServletRequest request,String goodId){
        String token = request.getHeader("token");
        shopCartService.incCount(token,goodId);
    }

    @RequestMapping("decCount")
    public void decCount(HttpServletRequest request,String goodId){
        String token = request.getHeader("token");
        shopCartService.decCount(token,goodId);
    }

    @RequestMapping("cancelCartGood")
    public resultMap cancelCartGood(HttpServletRequest request,String goodId){
        String token = request.getHeader("token");
        Integer integer = shopCartService.cancelGood(token, goodId);
        return  resultMapUtil.backResultMap("删除成功", integer, 100);
    }

    @RequestMapping("getMianIdFromCart")
    public resultMap getMianIdFromCart(){

        return null;
    }
}
