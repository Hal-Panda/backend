package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.goodDescribeImg;
import com.xh.b20220105backend.entity.goodDescribeImgExample;
import com.xh.b20220105backend.mapper.goodDescribeImgMapper;
import com.xh.b20220105backend.service.goodDescribeImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class goodDescribeImgServiceImpl implements goodDescribeImgService {
    @Autowired
    private goodDescribeImgMapper goodDescribeImgMapper;
    @Override
    public List<goodDescribeImg> getGoodDescribeImgs(String goodDescribeImgId) {
        goodDescribeImgExample goodDescribeImgExample = new goodDescribeImgExample();
        goodDescribeImgExample.createCriteria().andShowimgidEqualTo(goodDescribeImgId);
        List<goodDescribeImg> goodDescribeImgs = goodDescribeImgMapper.selectByExample(goodDescribeImgExample);

        return goodDescribeImgs;
    }
}
