package com.xh.b20220105backend.entity.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class getPayData {

   private ArrayList<Integer> odlisttdata;
   private String psw;
}
