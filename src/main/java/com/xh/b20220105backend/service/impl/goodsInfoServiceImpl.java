package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.goodsInfo;
import com.xh.b20220105backend.mapper.goodsInfoMapper;
import com.xh.b20220105backend.mapper.sellGoodsMapper;
import com.xh.b20220105backend.mapper.showImgMapper;
import com.xh.b20220105backend.service.goodsInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class goodsInfoServiceImpl implements goodsInfoService {
    @Resource
    private goodsInfoMapper goodsInfoMapper;


    @Resource
    private showImgMapper showImgMapper;


    @Override
    public goodsInfo selectOneById(Integer id) {
        goodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(id);
        return goodsInfo;
    }
}
