package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.goodsInfo;
import com.xh.b20220105backend.entity.goodsInfoExample;
import com.xh.b20220105backend.mapper.goodsInfoMapper;
import com.xh.b20220105backend.service.goodsInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class goodsInfoServiceImpl implements goodsInfoService {
    @Resource
    private goodsInfoMapper goodsInfoMapper;


    @Override
    public goodsInfo selectOneById(Integer id) {
        goodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(id);
        return goodsInfo;
    }

    @Override
    public List<goodsInfo> selectListByGoodId(List<Integer> goodIdList) {
        goodsInfoExample goodsInfoExample = new goodsInfoExample();
        goodsInfoExample.createCriteria().andGoodidIn(goodIdList);
        List<goodsInfo> goodsInfosList = goodsInfoMapper.selectByExample(goodsInfoExample);

        return goodsInfosList;
    }

    @Override
    public Integer sellgoodIdByGoodid(Integer goodid) {


        return null;
    }
}
