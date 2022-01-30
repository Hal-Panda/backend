package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.classify;
import com.xh.b20220105backend.entity.classifyExample;
import com.xh.b20220105backend.mapper.classifyMapper;
import com.xh.b20220105backend.service.classifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class classifyServiceImpl implements classifyService {
    @Resource
    private classifyMapper classifyMapper;
    @Override
    public List<classify> selectClassify(String classifyLevel) {
        classifyExample classifyExample = new classifyExample();
        classifyExample.createCriteria().andClassifylevelEqualTo(classifyLevel);
        List<classify> classifies = classifyMapper.selectByExample(classifyExample);
        return classifies;
    }
}
