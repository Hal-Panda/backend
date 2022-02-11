package com.xh.b20220105backend.service;

import com.xh.b20220105backend.entity.goodsNode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

public interface goodsNodeService {
    List<goodsNode> selectNodeList(Integer nodeId);

    Integer selectNodeIdByGoodId(Integer gooid);

    Integer isOverNodeId(Integer nodeId);
}
