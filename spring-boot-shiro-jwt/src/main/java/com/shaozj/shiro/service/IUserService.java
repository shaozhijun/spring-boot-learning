package com.shaozj.shiro.service;

import com.shaozj.shiro.model.User;

public interface IUserService {
	
	public User findByUsername(String username);
}
