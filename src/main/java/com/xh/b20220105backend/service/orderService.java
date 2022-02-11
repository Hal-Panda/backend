package com.xh.b20220105backend.service;

import com.xh.b20220105backend.entity.goodOrder;
import com.xh.b20220105backend.entity.goodsInfo;

import java.math.BigDecimal;
import java.util.List;

public interface orderService {
    List<goodOrder> createOrderByList(String token, List<goodsInfo> goodList, String userName, String userTel, String userAddress);

    Integer checkOrderList(List<Integer> orderList);

    Integer changeOrderRunStatus(List<Integer> orderList);

    BigDecimal getListSumMoney(List<Integer> orderList);

    Integer cancelOrderList(List<Integer> orderList);

    List<goodOrder> getAllOrderByUser(String token);

    List<goodOrder> getStatusOrderByUser(String token,Integer status);

    Integer finishOrderByUser(String token,Integer id);

    goodOrder selectOneOrderById(String token,Integer id);

    Integer deleteOrderById(String token,Integer id);
}
