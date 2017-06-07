package com.ssm.service;

import java.util.List;

import com.ssm.modal.User;


public interface UserService {
	User selectUserById(String userId);
	User findByName(String name);  
}
