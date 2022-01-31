package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.goodsNode;
import com.xh.b20220105backend.entity.goodsNodeExample;
import com.xh.b20220105backend.mapper.goodsNodeMapper;
import com.xh.b20220105backend.service.goodsNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class goodsNodeServiceImpl implements goodsNodeService {
    @Resource
    private goodsNodeMapper goodsNodeMapper;

    @Override
    public List<goodsNode> selectNodeList(Integer nodeId) {
        goodsNodeExample goodsNodeExample=new goodsNodeExample();
        goodsNodeExample.createCriteria().andNodeidEqualTo(nodeId);
        List<goodsNode> goodsNodes = goodsNodeMapper.selectByExample(goodsNodeExample);

        return goodsNodes;
    }
}
