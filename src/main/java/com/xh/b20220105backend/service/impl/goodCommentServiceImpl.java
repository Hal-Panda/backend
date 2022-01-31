package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.goodComment;
import com.xh.b20220105backend.entity.goodCommentExample;
import com.xh.b20220105backend.mapper.goodCommentMapper;
import com.xh.b20220105backend.service.goodCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class goodCommentServiceImpl implements goodCommentService {
    @Resource
    private goodCommentMapper goodCommentMapper;

    @Override
    public List<goodComment> getCommentsData(Integer commentId) {
        goodCommentExample goodCommentExample = new goodCommentExample();
        goodCommentExample.createCriteria().andCommentidEqualTo(commentId);
        List<goodComment> goodComments = goodCommentMapper.selectByExample(goodCommentExample);
        return goodComments;
    }
}
