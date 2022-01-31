package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.goodImg;
import com.xh.b20220105backend.entity.goodImgExample;
import com.xh.b20220105backend.mapper.goodImgMapper;
import com.xh.b20220105backend.service.goodImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class goodImgServiceImpl implements goodImgService {
    @Resource
    private goodImgMapper goodImgMapper;

    @Override
    public List<goodImg> showGoodImgs(Integer goodImgId) {
        goodImgExample goodImgExample = new goodImgExample();
        goodImgExample.createCriteria().andGoodimgidEqualTo(goodImgId);
        List<goodImg> goodImgs = goodImgMapper.selectByExample(goodImgExample);
        return goodImgs;
    }
}
