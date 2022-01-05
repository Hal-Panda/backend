package com.xh.b20220105backend.controller;


import com.xh.b20220105backend.entity.account;
import com.xh.b20220105backend.service.accountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class testController {
    @Autowired
    private accountService accountService;

    @RequestMapping("/test")
    public String showAll(){
        List<account> accounts = accountService.showAll();
        return accounts.toString();
    }
}
