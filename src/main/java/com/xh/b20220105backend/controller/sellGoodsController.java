package com.xh.b20220105backend.controller;

import com.xh.b20220105backend.entity.request.pageData;
import com.xh.b20220105backend.entity.response.resultMap;
import com.xh.b20220105backend.entity.sellGoods;
import com.xh.b20220105backend.service.sellGoodsService;
import com.xh.b20220105backend.util.resultMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sellGoods")
public class sellGoodsController {
    @Autowired
    private sellGoodsService sellGoodsService;



    @RequestMapping("selectAll")
    public resultMap seleceAllByPage(pageData pageData){

        List<sellGoods> sellGoods = sellGoodsService.selectAllByPage(pageData);
        resultMap resultMap = resultMapUtil.backResultMap("查询成功", sellGoods, 100);
        return resultMap;
    }


}
