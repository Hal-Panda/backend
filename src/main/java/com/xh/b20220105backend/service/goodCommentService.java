package com.xh.b20220105backend.service;

import com.xh.b20220105backend.entity.goodComment;

import java.util.List;

public interface goodCommentService {
    List<goodComment> getCommentsData(Integer commentId);
}
