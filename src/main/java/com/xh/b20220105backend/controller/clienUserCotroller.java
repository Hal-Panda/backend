package com.xh.b20220105backend.controller;

import com.xh.b20220105backend.entity.clienUser;
import com.xh.b20220105backend.entity.goodsLikeRecord;
import com.xh.b20220105backend.entity.request.loginData;
import com.xh.b20220105backend.entity.response.resultMap;
import com.xh.b20220105backend.service.clienUserService;
import com.xh.b20220105backend.service.goodsLikeRecordService;
import com.xh.b20220105backend.service.sellGoodsService;
import com.xh.b20220105backend.util.JwtUtil;
import com.xh.b20220105backend.util.resultMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/cuser")
public class clienUserCotroller {
    @Autowired
    private clienUserService clienUserService;

    @Autowired
    private goodsLikeRecordService goodsLikeRecordService;

    @Autowired
    private sellGoodsService sellGoodsService;

    @PostMapping("open/loginBP")
    public resultMap loginByPassword(@RequestBody loginData loginData){
        resultMap resultMap = clienUserService.loginByPassword(loginData.getUserId(), loginData.getUserPassword());
        return resultMap;
    }

    @RequestMapping("getUserInfo")
    public resultMap getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        clienUser baseUserInfo = clienUserService.getBaseUserInfo(token);
        resultMap resultMap = resultMapUtil.backResultMap("查询成功", baseUserInfo, 100);
        return resultMap;
    }

    @RequestMapping("getUserLikeInfo")
    public resultMap getUserLikeInfo(HttpServletRequest request,Integer sellGoodId){
        String token = request.getHeader("token");
        List<goodsLikeRecord> likeInfo = goodsLikeRecordService.getLikeInfo(token, sellGoodId,1);
        if (!likeInfo.isEmpty()){
            resultMap result = resultMapUtil.backResultMap("查询成功", 0, 100);
            return result;
        }
        else {
            resultMap result = resultMapUtil.backResultMap("查询成功", 1, 100);
            return result;
        }
    }

    @RequestMapping("reduceLike")
    public resultMap reduceLike(HttpServletRequest request,Integer sellGoodId){
        String token = request.getHeader("token");
        Integer flatOne = goodsLikeRecordService.reduceLike(token, sellGoodId);
        Integer flatTwo = sellGoodsService.reduceLikeCount(sellGoodId);
        if (flatOne==1&&flatTwo==1){
            resultMap result = resultMapUtil.backResultMap("修改成功", 1, 100);
            return result;
        }
        else{
            resultMap result = resultMapUtil.backResultMap("修改失败", 0, 100);
            return result;
        }
    }

    @RequestMapping("addLike")
    public resultMap addLike(HttpServletRequest request,Integer sellGoodId){
        String token = request.getHeader("token");
        Integer flatOne = goodsLikeRecordService.addLike(token, sellGoodId);
        Integer flatTwo = sellGoodsService.addLikeCount(sellGoodId);
        if (flatOne==1&&flatTwo==1){
            resultMap result = resultMapUtil.backResultMap("修改成功", 1, 100);
            return result;
        }
        else{
            resultMap result = resultMapUtil.backResultMap("修改失败", 0, 100);
            return result;
        }
    }

    @RequestMapping("enterVoid")
    public resultMap enterVoid(){
        resultMap result = resultMapUtil.backResultMap("成功", "", 100);
        return result;
    }

}
