package com.xh.b20220105backend.service;

import com.xh.b20220105backend.entity.request.shopCartData;

import java.util.List;

public interface shopCartService {
    Boolean addShopCart(shopCartData shopCartData,String token);

    List<shopCartData> showCart(String token);

    void incCount(String token,String goodId);

    void decCount(String token,String goodId);

    Integer cancelGood(String token,String goodId);
}
