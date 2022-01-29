package com.xh.b20220105backend.entity.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class resultMap {
    private String msg;
    private Object data;
    private Integer code;
}
