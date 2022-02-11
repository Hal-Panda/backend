package com.xh.b20220105backend.controller;

import com.xh.b20220105backend.entity.goodOrder;
import com.xh.b20220105backend.entity.goodsInfo;
import com.xh.b20220105backend.entity.request.getPayData;
import com.xh.b20220105backend.entity.response.resultMap;
import com.xh.b20220105backend.service.clienUserService;
import com.xh.b20220105backend.service.goodsInfoService;
import com.xh.b20220105backend.service.orderService;
import com.xh.b20220105backend.service.packetInfoService;
import com.xh.b20220105backend.util.resultMapUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("order")
public class orderController {

    @Autowired
    private goodsInfoService goodsInfoService;

    @Autowired
    private orderService orderService;

    @Autowired
    private packetInfoService packetInfoService;



    @RequestMapping("getGoodInfo")
    public resultMap getGoodInfo(@RequestBody Map<String,List<String>> goodList){

//        ArrayList<String> list = (ArrayList<String>) goodList;
        List<String> list = goodList.get("goodList");
        List<Integer> idList=new ArrayList<>();
        List<String> countList=new ArrayList<>();
        List<String> imgAddressList=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
//            String[] split = list.get(i).split(",");
            String[] split = list.get(i).split(",",3);
            idList.add(Integer.valueOf(split[0]));
            countList.add(split[1]);
            imgAddressList.add(split[2]);
        }
        List<goodsInfo> goodsInfos = goodsInfoService.selectListByGoodId(idList);
        for (int i = 0; i < list.size(); i++) {
            String getCount=null;
            String getImgAddress="";
            for (int j = 0; j < idList.size(); j++) {
                if (goodsInfos.get(i).getGoodid().equals(idList.get(j))){
                    getCount=countList.get(j);
                    getImgAddress=imgAddressList.get(j);
                }
            }
            goodsInfos.get(i).setOther(getCount);
            goodsInfos.get(i).setShowimgid(getImgAddress);
        }
        resultMap resultMap = resultMapUtil.backResultMap("查询成功", goodsInfos, 100);
        return resultMap;
    }

    @PostMapping("createOrder")
    public resultMap createOrder(@RequestBody  Map<String,String> goodMap, HttpServletRequest request){
        String token = request.getHeader("token");
        String list = goodMap.get("goodMap");
        String userName = goodMap.get("userName");
        String userTel = goodMap.get("userTel");
        String userAddress= goodMap.get("userAddress");
        JSONArray jsonArray = JSONArray.fromObject(list);
        List<goodsInfo> goodList = JSONArray.toList(jsonArray, new goodsInfo() , new JsonConfig());
        List<goodOrder> orderByList = orderService.createOrderByList(token, goodList, userName, userTel, userAddress);
        resultMap resultMap = resultMapUtil.backResultMap("查询成功", orderByList, 100);
        return  resultMap;
    }

    @PostMapping("payByPacket")
    public resultMap payByPacket(@RequestBody getPayData getPayData,HttpServletRequest request){
        String token = request.getHeader("token");
        String psw = getPayData.getPsw();
        ArrayList<Integer> odlisttdata = getPayData.getOdlisttdata();
        Integer orderFlat = orderService.checkOrderList(odlisttdata);
        if (orderFlat==0){
          return  resultMapUtil.backResultMap("订单不存在、过期或缺失，请重新下单", "-1", 100);
        }
        Integer pswFlat = packetInfoService.checkPswOfClien(token, psw);
        if (pswFlat==0){
            return  resultMapUtil.backResultMap("用户信息错误，请重新下单", "-1", 100);
        }
        else if (pswFlat==-1){
            return  resultMapUtil.backResultMap("密码错误", "0", 100);
        }
        BigDecimal listSumMoney = orderService.getListSumMoney(odlisttdata);
        if (listSumMoney.compareTo(new BigDecimal(-1))==0){
            return  resultMapUtil.backResultMap("获取订单信息出错，请重试", "-1", 100);
        }
        Integer payFlat = packetInfoService.reduceMoney(token, listSumMoney);
        if (payFlat==0){
            return  resultMapUtil.backResultMap("信息出错，请重新下单", "-1", 100);
        }
        else if (payFlat==-1){
            return  resultMapUtil.backResultMap("余额不足", "0", 100);
        }
        else if (payFlat==-2){
            return  resultMapUtil.backResultMap("扣款失败，请重试", "0", 100);
        }
        Integer changeFlat = orderService.changeOrderRunStatus(odlisttdata);
        if (changeFlat==-1||changeFlat==0){
            return  resultMapUtil.backResultMap("流程出错，若有损失，请联系客服或管理员", "-2", 100);
        }
        else if (changeFlat==1){
            return  resultMapUtil.backResultMap("下单成功", "1", 100);
        }
        else {
            return  resultMapUtil.backResultMap("未知错误，若有损失，请联系客服或管理员", "-2", 100);
        }
    }

    @PostMapping("cancelOrder")
    public resultMap cancelOrder(@RequestBody Map<String,List<Integer>> map){
        List<Integer> passOrderList = map.get("passOrderList");
        orderService.cancelOrderList(passOrderList);
        return  resultMapUtil.backResultMap("取消成功", "", 100);
    }

    @RequestMapping("getAllOrderInfo")
    public resultMap getAllOrderInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        List<goodOrder> allOrderByUser = orderService.getAllOrderByUser(token);
        return  resultMapUtil.backResultMap("查询成功", allOrderByUser, 100);
    }

    @RequestMapping("getStatusOrderInfo")
    public resultMap getStatusOrderInfo(HttpServletRequest request,Integer status){
        String token = request.getHeader("token");
        List<goodOrder> allOrderByUser = orderService.getStatusOrderByUser(token,status);
        return  resultMapUtil.backResultMap("查询成功", allOrderByUser, 100);
    }
    @RequestMapping("finishOrder")
    public resultMap finishOrder(HttpServletRequest request,Integer passId){
        String token = request.getHeader("token");
        Integer flat = orderService.finishOrderByUser(token, passId);
        if (flat==-1){
            return  resultMapUtil.backResultMap("错误警告：跨账号修改订单！", "-1", 100);
        }
        else if (flat==0){
            return  resultMapUtil.backResultMap("修改失败,请重试！", "0", 100);
        }
        else if (flat==1){
            return  resultMapUtil.backResultMap("成功！", "1", 100);
        }
        else {
            return  resultMapUtil.backResultMap("未知错误,请重试！", "0", 100);
        }
    }

    @RequestMapping("selectOneOrderById")
    public resultMap selectOneOrderById(HttpServletRequest request,Integer passId){
        String token = request.getHeader("token");
        goodOrder goodOrder = orderService.selectOneOrderById(token, passId);
        if (goodOrder==null){
            return  resultMapUtil.backResultMap("失败，请重试！", "0", 100);
        }
        return  resultMapUtil.backResultMap("成功", goodOrder, 100);
    }

    @RequestMapping("deleteOrderById")
    public resultMap deleteOrderById(HttpServletRequest request,Integer passId){
        String token = request.getHeader("token");
        Integer flat = orderService.deleteOrderById(token, passId);
        if (flat==0){
            return  resultMapUtil.backResultMap("删除失败，请重试！", "0", 100);
        }
        return  resultMapUtil.backResultMap("成功！", "1", 100);
    }
}
