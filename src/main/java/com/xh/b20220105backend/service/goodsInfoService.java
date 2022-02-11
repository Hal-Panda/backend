package com.xh.b20220105backend.service;

import com.xh.b20220105backend.entity.goodsInfo;

import java.util.List;

public interface  goodsInfoService {
    goodsInfo selectOneById(Integer id);

    List<goodsInfo> selectListByGoodId(List<Integer> goodIdList);

    Integer sellgoodIdByGoodid(Integer goodid);
}
