package com.xh.b20220105backend.service.impl;

import com.xh.b20220105backend.entity.account;
import com.xh.b20220105backend.entity.accountExample;
import com.xh.b20220105backend.mapper.accountMapper;
import com.xh.b20220105backend.service.accountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class accountServiceImpl implements accountService {
    @Autowired
    private accountMapper accountMapper;

    @Override
    public List<account> showAll() {
        List<account> accounts = accountMapper.selectByExample(new accountExample());
        return accounts;
    }
}
