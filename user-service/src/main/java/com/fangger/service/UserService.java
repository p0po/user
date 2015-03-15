package com.fangger.service;

import com.fangger.dao.mysql.model.User;

import java.util.List;

/**
 * 测试
 */
public interface UserService {
	public List<User> getAllUser();
    public int addUser(User user);
    public void editUser(User user);
    public void delUser(int id);
    public User getUser(int id);
    public User getUserByAccount(String account);
    public User login(String account,String password);
    public boolean checkAccount(String account);
    public void resetPassword(User user);
    public int getTotalNum();
    public List<User> getUserByPage(int start,int limit);
}
