package com.xh.b20220105backend.controller;

import com.xh.b20220105backend.entity.clienUser;
import com.xh.b20220105backend.entity.goodsLikeRecord;
import com.xh.b20220105backend.entity.request.loginData;
import com.xh.b20220105backend.entity.request.passwordData;
import com.xh.b20220105backend.entity.request.userMainInfo;
import com.xh.b20220105backend.entity.response.resultMap;
import com.xh.b20220105backend.service.clienUserService;
import com.xh.b20220105backend.service.goodsLikeRecordService;
import com.xh.b20220105backend.service.packetInfoService;
import com.xh.b20220105backend.service.sellGoodsService;
import com.xh.b20220105backend.util.JwtUtil;
import com.xh.b20220105backend.util.resultMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cuser")
public class clienUserCotroller {
    @Autowired
    private clienUserService clienUserService;

    @Autowired
    private goodsLikeRecordService goodsLikeRecordService;

    @Autowired
    private sellGoodsService sellGoodsService;

    @Autowired
    private packetInfoService packetInfoService;

    @PostMapping("open/loginBP")
    public resultMap loginByPassword(@RequestBody loginData loginData) {
        resultMap resultMap = clienUserService.loginByPassword(loginData.getUserId(), loginData.getUserPassword());
        return resultMap;
    }

    @RequestMapping("getUserInfo")
    public resultMap getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        clienUser baseUserInfo = clienUserService.getBaseUserInfo(token);
        resultMap resultMap = resultMapUtil.backResultMap("查询成功", baseUserInfo, 100);
        return resultMap;
    }

    @RequestMapping("getUserMainInfo")
    public resultMap getUserMainInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        clienUser baseUserInfo = clienUserService.getMainUserInfo(token);
        resultMap resultMap = resultMapUtil.backResultMap("查询成功", baseUserInfo, 100);
        return resultMap;
    }

    @RequestMapping("getUserLikeInfo")
    public resultMap getUserLikeInfo(HttpServletRequest request, Integer sellGoodId) {
        String token = request.getHeader("token");
        List<goodsLikeRecord> likeInfo = goodsLikeRecordService.getLikeInfo(token, sellGoodId, 1);
        if (!likeInfo.isEmpty()) {
            resultMap result = resultMapUtil.backResultMap("查询成功", 0, 100);
            return result;
        } else {
            resultMap result = resultMapUtil.backResultMap("查询成功", 1, 100);
            return result;
        }
    }

    @PostMapping("changeMainInfo")
    public resultMap changeMainInfo(HttpServletRequest request,@RequestBody userMainInfo userMainInfo) {
        String token = request.getHeader("token");
        Integer flat = clienUserService.changeMainUserInfo(token, userMainInfo);
        if (flat==1){
            return resultMapUtil.backResultMap("修改成功", 1, 100);
        }
        else {
            return resultMapUtil.backResultMap("修改失败", 0, 100);
        }
    }

    @RequestMapping("reduceLike")
    public resultMap reduceLike(HttpServletRequest request, Integer sellGoodId) {
        String token = request.getHeader("token");
        Integer flatOne = goodsLikeRecordService.reduceLike(token, sellGoodId);
        Integer flatTwo = sellGoodsService.reduceLikeCount(sellGoodId);
        if (flatOne == 1 && flatTwo == 1) {
            resultMap result = resultMapUtil.backResultMap("修改成功", 1, 100);
            return result;
        } else {
            resultMap result = resultMapUtil.backResultMap("修改失败", 0, 100);
            return result;
        }
    }

    @RequestMapping("addLike")
    public resultMap addLike(HttpServletRequest request, Integer sellGoodId) {
        String token = request.getHeader("token");
        Integer flatOne = goodsLikeRecordService.addLike(token, sellGoodId);
        Integer flatTwo = sellGoodsService.addLikeCount(sellGoodId);
        if (flatOne == 1 && flatTwo == 1) {
            resultMap result = resultMapUtil.backResultMap("修改成功", 1, 100);
            return result;
        } else {
            resultMap result = resultMapUtil.backResultMap("修改失败", 0, 100);
            return result;
        }
    }

    @RequestMapping("enterVoid")
    public resultMap enterVoid() {
        resultMap result = resultMapUtil.backResultMap("成功", "", 100);
        return result;
    }

    @PostMapping("hiden/changeAccoutPsw")
    public resultMap changeAccoutPsw(HttpServletRequest request,@RequestBody passwordData passwordData){
        String token = request.getHeader("token");
        Integer flat = clienUserService.changeUserPassword(token, passwordData);
        if (flat==-1){
            return resultMapUtil.backResultMap("原密码错误", 0, 100);
        }
        else if (flat==-2){
            return resultMapUtil.backResultMap("新密码格式错误", 0, 100);
        }
        else if (flat==1){
            return resultMapUtil.backResultMap("修改成功", 1, 100);
        }
        else {
            return resultMapUtil.backResultMap("修改失败，请重试", 0, 100);
        }
    }

    @PostMapping("hiden/changePacketPsw")
    public resultMap changePacketPsw(HttpServletRequest request,@RequestBody passwordData passwordData){
        String token = request.getHeader("token");
        Integer flat =packetInfoService.changePacketPassword(token,passwordData);
        if (flat==-2){
            return resultMapUtil.backResultMap("原密码错误", 0, 100);
        }
        else if (flat==-1){
            return resultMapUtil.backResultMap("新密码格式错误", 0, 100);
        }
        else if (flat==1){
            return resultMapUtil.backResultMap("修改成功", 1, 100);
        }
        else {
            return resultMapUtil.backResultMap("修改失败，请重试", 0, 100);
        }
    }

    @RequestMapping("hiden/checkMoney")
    public resultMap checkMoney(HttpServletRequest request){
        String token = request.getHeader("token");
        BigDecimal bigDecimal = packetInfoService.checkMoney(token);
        if (bigDecimal==null){
            return resultMapUtil.backResultMap("查询失败", -1, 100);
        }
        return resultMapUtil.backResultMap("查询成功", bigDecimal, 100);
    }

    @PostMapping("hiden/checkPayPassword")
    public resultMap checkPayPassword(HttpServletRequest request,@RequestBody Map<String,String> map){
        String token = request.getHeader("token");
        String psw = map.get("psw");
        Integer flat = packetInfoService.checkPassword(token, psw);
        if (flat==-1){
            return resultMapUtil.backResultMap("密码错误", -1, 100);
        }
        else if (flat==1){
            return resultMapUtil.backResultMap("查询成功", 1, 100);
        }
        else {
            return resultMapUtil.backResultMap("查询失败,稍后请重试", 0, 100);
        }
    }

}
