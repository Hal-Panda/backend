package com.xh.b20220105backend.service;

import com.xh.b20220105backend.entity.classify;

import java.util.List;

public interface classifyService {
    List<classify> selectClassify(String classifyLevel);
}
