package com.ssm.dao;


import org.springframework.stereotype.Repository;

import com.ssm.modal.User;


@Repository(value="userDao")
public interface UserDao {
	 public User selectUserById(String userId);
	 public User findByName(String name);
}
