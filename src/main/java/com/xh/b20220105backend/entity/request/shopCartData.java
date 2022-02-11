package com.xh.b20220105backend.entity.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class shopCartData {
    private Integer goodId;
    private Integer goodMainId;
    private BigDecimal goodPrice;
    private String goodName;
    private String goodImgId;
    private String firImgAddress;
    private Integer amount;
}
