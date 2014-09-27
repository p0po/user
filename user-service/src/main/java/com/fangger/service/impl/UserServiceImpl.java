package com.fangger.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fangger.dao.mysql.mapper.UserMapper;
import com.fangger.dao.mysql.model.User;
import com.fangger.dao.mysql.model.UserExample;
import com.fangger.service.UserService;

public class UserServiceImpl implements UserService{
	@Autowired
	UserMapper userMapper;
	@Override
	public List<User> getAllUser() {
		UserExample ue = new UserExample();
		List<User> userList = userMapper.selectByExample(ue);
		if(userList != null){
			return userList;
		}
		return Collections.emptyList();
	}
	
}
