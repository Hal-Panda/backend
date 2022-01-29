package com.xh.b20220105backend.util;

import com.xh.b20220105backend.entity.response.resultMap;

public class resultMapUtil {
    public static resultMap backResultMap(String msg,Object data,Integer code){
        resultMap resultMap = new resultMap();
        resultMap.setMsg(msg);
        resultMap.setData(data);
        resultMap.setCode(code);
        return resultMap;
    }
}
