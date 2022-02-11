package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.goodsLikeRecord;
import com.xh.b20220105backend.entity.goodsLikeRecordExample;
import com.xh.b20220105backend.mapper.goodsLikeRecordMapper;
import com.xh.b20220105backend.service.goodsLikeRecordService;
import com.xh.b20220105backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class goodsLikeRecordServiceImpl implements goodsLikeRecordService {
    @Resource
    private goodsLikeRecordMapper goodsLikeRecordMapper;



    @Override
    public List<goodsLikeRecord> getLikeInfo(String token, Integer sellGoodId,Integer status) {
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        goodsLikeRecordExample goodsLikeRecordExample = new goodsLikeRecordExample();
        goodsLikeRecordExample.createCriteria().andClienuseridEqualTo(userId).andSellgoodidEqualTo(sellGoodId).andStatusEqualTo(status);
        List<goodsLikeRecord> goodsLikeRecords = goodsLikeRecordMapper.selectByExample(goodsLikeRecordExample);
        return goodsLikeRecords;
    }

    @Override
    public Integer reduceLike(String token, Integer sellGoodId) {
        List<goodsLikeRecord> likeInfo = getLikeInfo(token, sellGoodId,1);
        if (!likeInfo.isEmpty()){
            goodsLikeRecord goodsLikeRecord = likeInfo.get(0);
            goodsLikeRecord.setStatus(0);
            int i = goodsLikeRecordMapper.updateByPrimaryKey(goodsLikeRecord);
            return i;
        }
        else {
            return 0;
        }

    }

    @Override
    public Integer addLike(String token, Integer sellGoodId) {
        List<goodsLikeRecord> likeInfo = getLikeInfo(token, sellGoodId,0);
        if (!likeInfo.isEmpty()){
            goodsLikeRecord goodsLikeRecord = likeInfo.get(0);
            goodsLikeRecord.setStatus(1);
            int i = goodsLikeRecordMapper.updateByPrimaryKey(goodsLikeRecord);
            return i;
        }
        else {
            Integer integer = insertLikeRecord(token, sellGoodId);
            return integer;
        }
    }

    public Integer insertLikeRecord(String token,Integer sellGoodid){
        String userId = JwtUtil.getTokenStringInfo(token, "userId");
        goodsLikeRecord goodsLikeRecord = new goodsLikeRecord();
        goodsLikeRecord.setStatus(1);
        goodsLikeRecord.setClienuserid(userId);
        goodsLikeRecord.setSellgoodid(sellGoodid);
        goodsLikeRecord.setId(null);
        int insert = goodsLikeRecordMapper.insertSelective(goodsLikeRecord);
        return insert;
    }
}
