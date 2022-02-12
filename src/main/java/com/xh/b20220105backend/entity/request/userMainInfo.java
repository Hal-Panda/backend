package com.xh.b20220105backend.entity.request;

import lombok.Data;

import java.util.Date;

@Data
public class userMainInfo {
    private String realName;
    private String userName;
    private Integer sex;
    private Date birthday;
    private String describe;
}
