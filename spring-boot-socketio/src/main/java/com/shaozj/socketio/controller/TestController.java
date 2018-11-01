package com.shaozj.socketio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shaozj.socketio.service.ISocketIOService;

@Controller
@RequestMapping("/aa")
public class TestController {
	
	@Autowired
	private ISocketIOService socketIOService;
	
	@RequestMapping("/index")
	public String index() {
		return "/index";
	}
	
	@RequestMapping("/test")
	public @ResponseBody String test(Integer count) {
		socketIOService.sendCount("queuecount", count);
		return "success";
	}
}
