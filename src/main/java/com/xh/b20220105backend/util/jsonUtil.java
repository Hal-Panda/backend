package com.xh.b20220105backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class jsonUtil {
    public static String toJson(Object data){
        ObjectMapper mapper=new ObjectMapper();
        String dataJson="";
        try {
            dataJson = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return dataJson;
    }
}
