package com.xh.b20220105backend.service;

import com.xh.b20220105backend.entity.goodsLikeRecord;

import java.util.List;

public interface goodsLikeRecordService {
    List<goodsLikeRecord> getLikeInfo(String token,Integer sellGoodId,Integer status);

    Integer reduceLike(String token,Integer sellGoodId);

    Integer addLike(String token,Integer sellGoodId);
}
