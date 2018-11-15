package com.shaozj.socketio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/aa")
public class TestController {
	
//	@Autowired
//	private ISocketIOService socketIOService;
	
	@RequestMapping("/index")
	public String index() {
		
		
		return "/index";
	}
	
	@RequestMapping("/index2")
	public String index2() {
		return "/index2";
	}
	
	@RequestMapping("/test")
	public @ResponseBody String test(Integer count) {
//		socketIOService.sendCount("queuecount", count);
		return "success";
	}
	
	@RequestMapping("/chat1")
	public String chat1() {
		return "/chat1";
	}
	
	@RequestMapping("/chat2")
	public String chat2() {
		return "/chat2";
	}
}
