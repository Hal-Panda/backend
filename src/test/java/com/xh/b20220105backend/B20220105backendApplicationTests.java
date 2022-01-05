package com.xh.b20220105backend;

import com.xh.b20220105backend.entity.account;
import com.xh.b20220105backend.entity.accountExample;
import com.xh.b20220105backend.mapper.accountMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class B20220105backendApplicationTests {

	@Autowired
	private accountMapper accountMapper;

	@Test
	void contextLoads() {
		accountExample example =new accountExample();
		for (account account : accountMapper.selectByExample(example)) {
			System.out.println(account);
		}
		}
	}


