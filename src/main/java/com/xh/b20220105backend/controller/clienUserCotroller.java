package com.xh.b20220105backend.controller;

import com.xh.b20220105backend.entity.request.loginData;
import com.xh.b20220105backend.entity.response.resultMap;
import com.xh.b20220105backend.service.clienUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cuser")
public class clienUserCotroller {
    @Autowired
    private clienUserService clienUserService;

    @PostMapping("open/loginBP")
    public resultMap loginByPassword(@RequestBody loginData loginData){
        resultMap resultMap = clienUserService.loginByPassword(loginData.getUserId(), loginData.getUserPassword());
        return resultMap;
    }


}
