package com.xh.b20220105backend.service;

import com.xh.b20220105backend.entity.goodDescribeImg;

import java.util.List;

public interface goodDescribeImgService {
    List<goodDescribeImg> getGoodDescribeImgs(String goodDescribeImgId);
}
