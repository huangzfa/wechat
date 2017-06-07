package com.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.dao.UserDao;
import com.ssm.modal.User;

@Service("userService") 
public class UserServiceImpl  implements UserService{
    @Autowired  
    private UserDao userDao;  
  

	public User selectUserById(String userId) {
		// TODO Auto-generated method stub
		return userDao.selectUserById(userId);  
	}


	public User findByName(String name) {
		// TODO Auto-generated method stub
		return userDao.findByName(name);
	}





}
