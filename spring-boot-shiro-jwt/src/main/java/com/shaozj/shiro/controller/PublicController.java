package com.shaozj.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shaozj.shiro.bean.Result;
import com.shaozj.shiro.bean.ResultGenerator;
import com.shaozj.shiro.jwt.JwtUtil;
import com.shaozj.shiro.model.User;
import com.shaozj.shiro.service.IUserService;

@Controller
@RequestMapping("/public")
public class PublicController {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PublicController.class);
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 
	 * <p>Description: �û���¼</p>
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	public Result<String> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		User user = userService.findByUsername(username);
		if (user.getPassword().equals(password)) {
			String token = JwtUtil.sign(username, password);
			return ResultGenerator.genSuccessResult(token);
		} else {
			throw new UnauthorizedException();
		}
	}
	
	/**
	 * <p>Description: �����û������Է��ʡ������û����οͷ��ʵĲ�ͬ</p>
	 * @return
	 */
	@GetMapping("/article")
	public Result<Object> article() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			LOGGER.info("�û�����");
			return ResultGenerator.genSuccessResult();
		} else {
			LOGGER.info("�οͷ���");
			return ResultGenerator.genSuccessResult();
		}
	}
	
	/**
	 * 
	 * <p>Description: ��¼���û��ſ��Է���</p>
	 * @return
	 */
	@GetMapping("/requireauth")
	@RequiresAuthentication
	public Result<Object> requireAuth() {
		return ResultGenerator.genSuccessResult();
	}
	
	/**
	 * 
	 * <p>Description: admin��ɫ�ſ��Է���</p>
	 * @return
	 */
	@GetMapping("/requirerole")
	@RequiresRoles("admin")
	public Result<Object> requireRole() {
		return ResultGenerator.genSuccessResult();
	}
	
	/**
	 * 
	 * <p>Description: ӵ��view��editȨ�޵Ĳſ��Է���</p>
	 * @return
	 */
	@GetMapping("/permission")
	@RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
	public Result<Object> requirePermission() {
		return ResultGenerator.genSuccessResult();
	}
	
	/**
	 * 
	 * <p>Description: δ��Ȩ��ת401</p>
	 * @return
	 */
	@RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<Object> unauthorized() {
		return ResultGenerator.genSuccessResult();
    }
}
