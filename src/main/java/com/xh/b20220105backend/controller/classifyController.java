package com.xh.b20220105backend.controller;

import com.xh.b20220105backend.entity.classify;
import com.xh.b20220105backend.entity.response.resultMap;
import com.xh.b20220105backend.service.classifyService;
import com.xh.b20220105backend.util.resultMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classify")
public class classifyController {
    @Autowired
    private classifyService classifyService;

    @RequestMapping("getInfo")
    public resultMap selectClassify(String classifyLevel){
        List<classify> classifies = classifyService.selectClassify(classifyLevel);
        resultMap resultMap = resultMapUtil.backResultMap("查询成功", classifies, 100);
        return resultMap;
    }

}
