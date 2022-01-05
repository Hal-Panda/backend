package com.xh.b20220105backend.mapper;

import com.xh.b20220105backend.entity.account;
import com.xh.b20220105backend.entity.accountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface accountMapper {
    long countByExample(accountExample example);

    int deleteByExample(accountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(account record);

    int insertSelective(account record);

    List<account> selectByExample(accountExample example);

    account selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") account record, @Param("example") accountExample example);

    int updateByExample(@Param("record") account record, @Param("example") accountExample example);

    int updateByPrimaryKeySelective(account record);

    int updateByPrimaryKey(account record);
}