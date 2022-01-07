package com.xh.b20220105backend.controller;


import com.xh.b20220105backend.entity.account;
import com.xh.b20220105backend.entity.goods;
import com.xh.b20220105backend.service.accountService;
import com.xh.b20220105backend.service.goodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class testController {
    @Autowired
    private accountService accountService;
    @Autowired
    private goodService goodService;

    @RequestMapping("/test")
    public List<goods> showAll(){
        List<goods> goods = goodService.showAll();
        return goods;
    }
}
