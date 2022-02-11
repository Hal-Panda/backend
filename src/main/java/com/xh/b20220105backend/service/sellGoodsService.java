package com.xh.b20220105backend.service;

import com.xh.b20220105backend.entity.request.pageData;
import com.xh.b20220105backend.entity.sellGoods;

import java.util.List;

public interface sellGoodsService {
    List<sellGoods> selectAllByPage(pageData pageData);

    sellGoods selectOneById(Integer id);

    Integer reduceLikeCount(Integer sellGoodId);

    Integer addLikeCount(Integer sellGoodId);

    Integer selectSellGoodIdByNodeId(Integer nodeId);

    Integer selectSellGoodIdByGoodId(Integer goodid);

}
