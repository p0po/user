package com.fangger.service;

import java.util.List;

import com.fangger.dao.mysql.model.User;
import com.fangger.exception.UserException;

/**
 * 测试
 */
public interface UserService {
	public List<User> getAllUser();
    public int addUser(User user) throws UserException;
    public void editUser(User user) throws UserException;
    public void delUser(int id);
    public User getUser(int id);
    public User getUserByAccount(String account);
    public User login(String account,String password);
    public boolean checkAccount(String account);
    public void resetPassword(User user) throws UserException;
    public int getTotalNum();
    public List<User> getUserByPage(int start,int limit);
}
