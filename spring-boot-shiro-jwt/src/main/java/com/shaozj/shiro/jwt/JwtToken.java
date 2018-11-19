package com.shaozj.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 
 * <p>
 * ClassName: JwtToken
 * </p>
 * <p>
 * Description: JwtToken差不多就是Shiro用戶名密码的载体。
 * 因为做前后端分离，服务器无需保存用户状态，所以不需要RememberMe这类功能，
 * </p>
 * <p>
 * Author: szj
 * </p>
 * <p>
 * Date: 2018年11月7日
 * </p>
 */
public class JwtToken implements AuthenticationToken {

	/**
	 * <p>Field serialVersionUID: TODO</p>
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String token;
	
	public JwtToken(String token) {
		this.token = token;
	}

	@Override
	public Object getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}
}
