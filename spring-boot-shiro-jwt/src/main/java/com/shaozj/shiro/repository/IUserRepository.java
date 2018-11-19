package com.shaozj.shiro.repository;

import org.springframework.data.repository.CrudRepository;

import com.shaozj.shiro.model.User;

public interface IUserRepository extends CrudRepository<User, Long>  {
	
	public User findByUsername(String username);
}
