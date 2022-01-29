package com.xh.b20220105backend.beanManage;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class testBean {

    @Bean("showNum")
    public Integer showNum() {

        return 0;
    }
}
