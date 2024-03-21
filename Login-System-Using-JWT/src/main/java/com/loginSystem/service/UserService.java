package com.loginSystem.service;

import java.util.List;

import com.loginSystem.Model.User;

public interface UserService {

	public User saveUser(User user);
	public String deleteUser(String id);
	public User updateUser(User user);
	public List<User> getAllUser();
	public User getSingleUser(String id);
	
}
