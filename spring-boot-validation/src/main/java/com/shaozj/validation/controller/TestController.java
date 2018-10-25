package com.shaozj.validation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.shaozj.validation.model.User;

@RestController
@RequestMapping("/")
public class TestController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("test")
	public String test(@Validated User user) {
		logger.info("user = {}", JSON.toJSONString(user));
		return null;
	}
	
}
