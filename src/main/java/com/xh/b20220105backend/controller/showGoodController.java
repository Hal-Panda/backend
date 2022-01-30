package com.xh.b20220105backend.controller;

import com.xh.b20220105backend.entity.goodsInfo;
import com.xh.b20220105backend.entity.response.resultMap;
import com.xh.b20220105backend.entity.sellGoods;
import com.xh.b20220105backend.service.goodsInfoService;
import com.xh.b20220105backend.service.sellGoodsService;
import com.xh.b20220105backend.util.resultMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/show")
public class showGoodController {
    @Autowired
    private sellGoodsService sellGoodsService;

    @Autowired
    private goodsInfoService goodsInfoService;

    @RequestMapping("mainGood/{id}")
    public resultMap showMainGood (@PathVariable Integer id){
        sellGoods sellGoods = sellGoodsService.selectOneById(id);
        goodsInfo goodsInfo = goodsInfoService.selectOneById(sellGoods.getMaingoodid());
        List<Object> list=new ArrayList<>();
        list.add(sellGoods);
        list.add(goodsInfo);
        resultMap resultMap = resultMapUtil.backResultMap("查询成功", list, 100);
        return resultMap;
    }
}
