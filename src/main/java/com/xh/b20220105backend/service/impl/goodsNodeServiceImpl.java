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

    @Override
    public Integer selectNodeIdByGoodId(Integer gooid) {
        goodsNodeExample goodsNodeExample = new goodsNodeExample();
        goodsNodeExample.createCriteria().andTodogoodidEqualTo(gooid);
        List<goodsNode> goodsNodes = goodsNodeMapper.selectByExample(goodsNodeExample);
        if (goodsNodes.size()>0){
            return goodsNodes.get(0).getNodeid();
        }
        else {
            return 0;
        }

    }

    @Override
    public Integer isOverNodeId(Integer nodeId) {
        Integer overNodeId=nodeId;
        goodsNodeExample goodsNodeExample = new goodsNodeExample();
        goodsNodeExample.createCriteria().andNextnodeidEqualTo(nodeId);
        List<goodsNode> goodsNodes = goodsNodeMapper.selectByExample(goodsNodeExample);
        if (goodsNodes.size()>0){
            Integer nodeid = goodsNodes.get(0).getNodeid();
             overNodeId = isOverNodeId(nodeid);
        }
        return overNodeId;
    }
}
