package com.fangger.service.impl;

import com.fangger.dao.mysql.mapper.UserMapper;
import com.fangger.dao.mysql.model.User;
import com.fangger.dao.mysql.model.UserExample;
import com.fangger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
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

    @Override
    public int addUser(User user) {
        if(user == null){
            throw  new IllegalArgumentException("无用户");
        }

        if (StringUtils.isEmpty(user.getAccount())){
            throw  new IllegalArgumentException("用户名不能为空");
        }

        if (StringUtils.isEmpty(user.getPassword())){
            throw  new IllegalArgumentException("密码不能为空");
        }

        if(checkAccount(user.getAccount())){
            throw new IllegalArgumentException("用户："+user.getAccount()+" 已经存在。");
        }

        user.setPassword(generatePassword(user.getPassword()));

        return userMapper.insert(user);
    }

    private String generatePassword(String pwd){

        return pwd;
    }

    @Override
    public void editUser(User user){
        if(user == null){
            throw  new IllegalArgumentException("无用户");
        }

        if(checkAccount(user.getAccount())){
            throw new IllegalArgumentException("用户："+user.getAccount()+" 已经存在。");
        }

        user.setPassword(null);

        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delUser(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User getUser(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUserByAccount(String account) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountEqualTo(account);
        List<User> userList = userMapper.selectByExample(example);
        return userList.size()==0?null:userList.get(0);
    }

    @Override
    public User login(String account, String password) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andAccountEqualTo(account)
                .andPasswordEqualTo(password);
        List<User> userList = userMapper.selectByExample(example);
        return userList.size()==0?null:userList.get(0);
    }

    @Override
    public boolean checkAccount(String account) {
        return getUserByAccount(account) == null?false:true;
    }

    @Override
    public void resetPassword(User user) {
        if(user == null){
            throw  new IllegalArgumentException("无用户");
        }
        user.setAccount(null);
        user.setPassword(generatePassword(user.getPassword()));
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int getTotalNum() {
        UserExample example = new UserExample();
        example.createCriteria().isValid();
        return userMapper.countByExample(example);
    }

    @Override
    public List<User> getUserByPage(int start, int limit) {
        UserExample example = new UserExample();

        return null;
    }


}

