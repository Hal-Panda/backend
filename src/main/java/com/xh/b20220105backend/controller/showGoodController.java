package com.xh.b20220105backend.controller;

import com.xh.b20220105backend.entity.*;
import com.xh.b20220105backend.entity.response.resultMap;
import com.xh.b20220105backend.service.*;
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

    @Autowired
    private goodsNodeService goodsNodeService;

    @Autowired
    private goodImgService goodImgService;

    @Autowired
    private goodCommentService goodCommentService;

    @Autowired
    private goodDescribeImgService goodDescribeImgService;

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

    @RequestMapping("nodesInfo")
    public resultMap showNodesInfo(Integer nodeId){
        List<goodsNode> goodsNodes = goodsNodeService.selectNodeList(nodeId);

        resultMap resultMap = resultMapUtil.backResultMap("查询成功", goodsNodes, 100);
        return resultMap;
    }

    @RequestMapping("goodBase/{goodId}")
    public resultMap goodBase(@PathVariable Integer goodId){
        goodsInfo goodsInfo = goodsInfoService.selectOneById(goodId);

        resultMap resultMap = resultMapUtil.backResultMap("查询成功", goodsInfo, 100);
        return resultMap;
    }

    @RequestMapping("goodImgs")
    public resultMap goodImgs(Integer goodImgId){
        List<goodImg> goodImgs = goodImgService.showGoodImgs(goodImgId);
        resultMap resultMap = resultMapUtil.backResultMap("查询成功", goodImgs, 100);
        return resultMap;
    }

    @RequestMapping("goodComments")
    public resultMap goodComments(Integer goodCommentId){
        List<goodComment> commentsData = goodCommentService.getCommentsData(goodCommentId);
        resultMap resultMap = resultMapUtil.backResultMap("查询成功", commentsData, 100);
        return resultMap;
    }

    @RequestMapping("goodDescribeImg")
    public resultMap goodDescribeImg(String goodDescribeImgId){
        List<goodDescribeImg> goodDescribeImgs = goodDescribeImgService.getGoodDescribeImgs(goodDescribeImgId);
        resultMap resultMap = resultMapUtil.backResultMap("查询成功", goodDescribeImgs, 100);
        return resultMap;
    }

    @RequestMapping("getSellGoodByGooid")
    public resultMap getSellGoodByGooid(Integer passId){
        Integer nodeid = goodsNodeService.selectNodeIdByGoodId(passId);
        if (nodeid==0){
            Integer id = sellGoodsService.selectSellGoodIdByGoodId(passId);
            if (id==0){
                return resultMapUtil.backResultMap("出错或该货架已被删除", "0", 100);
            }
            return resultMapUtil.backResultMap("成功！", id, 100);
        }
        Integer overNodeId = goodsNodeService.isOverNodeId(nodeid);
        Integer sellGoodId = sellGoodsService.selectSellGoodIdByNodeId(overNodeId);
        if (sellGoodId==0){
            return resultMapUtil.backResultMap("出错或该货架已被删除", "0", 100);
        }
        return resultMapUtil.backResultMap("成功", sellGoodId, 100);
    }
}
