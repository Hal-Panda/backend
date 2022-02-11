package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.goodOrder;
import com.xh.b20220105backend.entity.goodOrderExample;
import com.xh.b20220105backend.entity.goodsInfo;
import com.xh.b20220105backend.mapper.goodOrderMapper;
import com.xh.b20220105backend.service.goodsInfoService;
import com.xh.b20220105backend.service.orderService;
import com.xh.b20220105backend.service.packetInfoService;
import com.xh.b20220105backend.service.shopCartService;
import com.xh.b20220105backend.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class orderServiceImpl implements orderService {
    @Resource
    private goodOrderMapper goodOrderMapper;

    @Autowired
    private goodsInfoService goodsInfoService;

    @Autowired
    private shopCartService shopCartService;

    @Autowired
    private packetInfoService packetInfoService;

    @Override
    public List<goodOrder> createOrderByList(String token,List<goodsInfo> goodList, String userName, String userTel, String userAddress) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        List<Integer> goodIdList=new ArrayList<>();
        List<goodOrder> orderList =new ArrayList<>();
        for (goodsInfo goodsInfo : goodList) {
            Integer goodid = goodsInfo.getGoodid();
            goodIdList.add(goodid);
        }
        List<goodsInfo> goodMetaList = goodsInfoService.selectListByGoodId(goodIdList);
        for (int i = 0; i < goodMetaList.size(); i++) {
            goodOrder goodOrder = new goodOrder();
            goodsInfo goodsInfo = goodMetaList.get(i);
            goodsInfo.setShowimgid(goodList.get(i).getShowimgid());
            goodsInfo.setOther(goodList.get(i).getOther());
            if (goodsInfo.toString().equals(goodList.get(i).toString())) {
                goodOrder.setGoodid(goodsInfo.getGoodid());
                goodOrder.setGoodcount(Integer.valueOf(goodsInfo.getOther()));
                goodOrder.setGoodprice(goodsInfo.getGoodprice());
                goodOrder.setGoodfirimg(goodsInfo.getShowimgid());
                BigDecimal count=new BigDecimal(goodsInfo.getOther());
                BigDecimal goodprice = goodsInfo.getGoodprice().multiply(count);
                goodOrder.setGoodsummoney(goodprice);
                goodOrder.setGoodname(goodsInfo.getGoodname());
                goodOrder.setOrderbegintime(new Date());
                goodOrder.setOrderrunstatus(0);
                goodOrder.setUserid(userId);
                goodOrder.setOrderacceptname(userName);
                goodOrder.setOrderaccepttel(userTel);
                goodOrder.setOrderacceptaddress(userAddress);
                int flat = goodOrderMapper.insertSelective(goodOrder);
                if (flat>0){
                    orderList.add(goodOrder);
                    Integer goodid = goodOrder.getGoodid();
                    shopCartService.cancelGood(token, String.valueOf(goodid));
                }
            }
        }
        return orderList;
    }

    @Override
    public Integer checkOrderList(List<Integer> orderList) {
        int size = orderList.size();
        goodOrderExample goodOrderExample = new goodOrderExample();
        goodOrderExample.createCriteria().andIdIn(orderList).andOrderrunstatusEqualTo(0);
        List<goodOrder> goodOrders = goodOrderMapper.selectByExample(goodOrderExample);
        int slecteSize = goodOrders.size();
        if (size!=slecteSize){
            log.warn("订单不存在、过期或缺失");
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public Integer changeOrderRunStatus(List<Integer> orderList) {
        int size = orderList.size();
        int successCount=0;
        goodOrderExample goodOrderExample = new goodOrderExample();
        goodOrderExample.createCriteria().andIdIn(orderList).andOrderrunstatusEqualTo(0);
        List<goodOrder> goodOrders = goodOrderMapper.selectByExample(goodOrderExample);
        for (int i = 0; i < goodOrders.size(); i++) {
            goodOrders.get(i).setOrderrunstatus(1);
            goodOrders.get(i).setOrderfinishtime(new Date());
            int i1 = goodOrderMapper.updateByPrimaryKey(goodOrders.get(i));
            successCount+=i1;
        }
        if (successCount==size){
            return 1;
        }
        else if (successCount==0){
            return 0;
        }
        else {
            return -1;
        }
    }

    @Override
    public BigDecimal getListSumMoney(List<Integer> orderList) {
        int size = orderList.size();
        int successCount=0;
        BigDecimal sum=new BigDecimal(0);
        goodOrderExample goodOrderExample = new goodOrderExample();
        goodOrderExample.createCriteria().andIdIn(orderList).andOrderrunstatusEqualTo(0);
        List<goodOrder> goodOrders = goodOrderMapper.selectByExample(goodOrderExample);
        for (int i = 0; i < goodOrders.size(); i++) {
            BigDecimal goodsummoney = goodOrders.get(i).getGoodsummoney();
            sum=sum.add(goodsummoney);
            int i1 = goodOrderMapper.updateByPrimaryKey(goodOrders.get(i));
            successCount+=i1;
        }
        if (successCount==size){
            return sum;
        }
        else {
            return new BigDecimal(-1);
        }
    }

    @Override
    public Integer cancelOrderList(List<Integer> orderList) {
        goodOrderExample goodOrderExample = new goodOrderExample();
        goodOrderExample.createCriteria().andIdIn(orderList).andOrderrunstatusEqualTo(0);
        List<goodOrder> goodOrders = goodOrderMapper.selectByExample(goodOrderExample);
        for (int i = 0; i < goodOrders.size(); i++) {
            goodOrders.get(i).setOrderrunstatus(-1);
            goodOrders.get(i).setOrderovertime(new Date());
            int i1 = goodOrderMapper.updateByPrimaryKey(goodOrders.get(i));
        }
        return 1;
    }

    @Override
    public List<goodOrder> getAllOrderByUser(String token) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        goodOrderExample goodOrderExample = new goodOrderExample();
        goodOrderExample.createCriteria().andUseridEqualTo(userId).andOrderrunstatusNotEqualTo(-2);
        goodOrderExample.setOrderByClause("id desc");
        List<goodOrder> goodOrders = goodOrderMapper.selectByExample(goodOrderExample);
        return goodOrders;
    }

    @Override
    public List<goodOrder> getStatusOrderByUser(String token, Integer status) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        goodOrderExample goodOrderExample = new goodOrderExample();
        goodOrderExample.createCriteria().andUseridEqualTo(userId).andOrderrunstatusEqualTo(status);
        goodOrderExample.setOrderByClause("id desc");
        List<goodOrder> goodOrders = goodOrderMapper.selectByExample(goodOrderExample);
        return goodOrders;
    }

    @Override
    public Integer finishOrderByUser(String token, Integer id) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        goodOrder goodOrder = goodOrderMapper.selectByPrimaryKey(id);
        String orderUserid = goodOrder.getUserid();
        if (!userId.equals(orderUserid)){
            log.warn("finishOrderByUser:跨账号修改订单状态");
            return -1;
        }
        goodOrder.setOrderrunstatus(2);
        int i = goodOrderMapper.updateByPrimaryKey(goodOrder);
        if (i==1){
            return 1;
        }
        return 0;
    }

    @Override
    public goodOrder selectOneOrderById(String token, Integer id) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        goodOrder goodOrder = goodOrderMapper.selectByPrimaryKey(id);
        String orderUserid = goodOrder.getUserid();
        if (!userId.equals(orderUserid)){
            log.warn("selectOneOrderById:跨账号修改订单状态");
            return null;
        }

        return goodOrder;
    }

    @Override
    public Integer deleteOrderById(String token, Integer id) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        goodOrder goodOrder = goodOrderMapper.selectByPrimaryKey(id);
        String orderUserid = goodOrder.getUserid();
        if (!userId.equals(orderUserid)){
            log.warn("deleteOrderById:跨账号修改订单状态");
            return 0;
        }
        goodOrder.setOrderrunstatus(-2);
        int i = goodOrderMapper.updateByPrimaryKey(goodOrder);
        return i;
    }

}
