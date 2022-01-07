package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.goods;
import com.xh.b20220105backend.entity.goodsExample;
import com.xh.b20220105backend.mapper.goodsMapper;
import com.xh.b20220105backend.service.goodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class goodServiceImpl implements goodService {
    @Autowired
    private goodsMapper goodsMapper;

    @Override
    public List<goods> showAll() {
        List<goods> goods = goodsMapper.selectByExample(new goodsExample());
        return goods;
    }
}
