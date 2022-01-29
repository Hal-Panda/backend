package com.xh.b20220105backend.entity.request;

import lombok.Data;

@Data
public class pageData {
    private Integer pageNum;
    private Integer pageSize;
    private String orderKeys;
}
