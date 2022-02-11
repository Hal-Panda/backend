package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.goodImg;
import com.xh.b20220105backend.entity.request.shopCartData;
import com.xh.b20220105backend.service.goodImgService;
import com.xh.b20220105backend.service.shopCartService;
import com.xh.b20220105backend.util.JwtUtil;
import com.xh.b20220105backend.util.redisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class shopCartServiceImpl implements shopCartService {
    @Autowired
    private redisUtil redisUtil;

    @Autowired
    private goodImgService goodImgService;

    @Override
    public Boolean addShopCart(shopCartData shopCartData,String token) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        List<goodImg> goodImgs = goodImgService.showGoodImgs(Integer.valueOf(shopCartData.getGoodImgId()));
        String imgaddress="";
        if (!goodImgs.isEmpty()){
            imgaddress = goodImgs.get(0).getImgaddress();
        }
        shopCartData.setFirImgAddress(imgaddress);
        boolean isExsit = redisUtil.hHasKey(userId, String.valueOf(shopCartData.getGoodId()));
        if (isExsit){
            shopCartData hget = (shopCartData)redisUtil.hget(userId, String.valueOf(shopCartData.getGoodId()));
            Integer amount = hget.getAmount();
            shopCartData.setAmount(amount+1);
        }
        else {
            shopCartData.setAmount(1);
        }
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put(String.valueOf(shopCartData.getGoodId()),shopCartData);


        try {
            boolean flat = redisUtil.hmset(userId, stringObjectHashMap, 3600 * 24 * 30);
            return flat;
        }
        catch (Exception e){
            log.warn(e.getMessage());
            return false;
        }
    }

    @Override
    public List<shopCartData> showCart(String token) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        Map<Object, Object> hmget =  redisUtil.hmget(userId);
        List<shopCartData> shopCartList =new ArrayList<>();
        for(Map.Entry<Object, Object> entry : hmget.entrySet()){
            shopCartData mapValue = (shopCartData)entry.getValue();
            shopCartList.add(mapValue);
        }
        return shopCartList;
    }

    @Override
    public void incCount(String token,String goodId) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        shopCartData shopCart = (shopCartData)redisUtil.hget(userId, goodId);
        Integer amount = shopCart.getAmount();
        amount=amount+1;
        shopCart.setAmount(amount);
        redisUtil.hset(userId, goodId, shopCart);
    }

    @Override
    public void decCount(String token, String goodId) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        shopCartData shopCart = (shopCartData)redisUtil.hget(userId, goodId);
        Integer amount = shopCart.getAmount();
        if (amount>=1){
            amount=amount-1;
        }
        shopCart.setAmount(amount);
        redisUtil.hset(userId, goodId, shopCart);
    }

    @Override
    public Integer cancelGood(String token, String goodId) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        Long hdel = redisUtil.hdel(userId, goodId);
        return Math.toIntExact(hdel);
    }
}
