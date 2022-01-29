package com.xh.b20220105backend.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xh.b20220105backend.entity.request.pageData;
import com.xh.b20220105backend.entity.sellGoods;
import com.xh.b20220105backend.entity.sellGoodsExample;
import com.xh.b20220105backend.mapper.sellGoodsMapper;
import com.xh.b20220105backend.service.sellGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sellGoodsServiceImpl implements sellGoodsService {
    @Resource
    private sellGoodsMapper sellGoodsMapper;

    @Override
    public List<sellGoods> selectAllByPage(pageData pageData) {
        PageHelper.startPage(pageData.getPageNum(),pageData.getPageSize());
        sellGoodsExample sellGoodsExample = new sellGoodsExample();
        sellGoodsExample.setOrderByClause(pageData.getOrderKeys());
//        sellGoodsExample.setDistinct(true);
        List<sellGoods> sellGoods = sellGoodsMapper.selectByExample(sellGoodsExample);
        PageInfo<sellGoods> info=new PageInfo<>(sellGoods);
        return info.getList();
    }
}
