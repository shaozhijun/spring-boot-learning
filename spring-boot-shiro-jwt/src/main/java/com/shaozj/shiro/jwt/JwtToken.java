package com.shaozj.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 
 * <p>
 * ClassName: JwtToken
 * </p>
 * <p>
 * Description: JwtToken������Shiro�Ñ�����������塣
 * ��Ϊ��ǰ��˷��룬���������豣���û�״̬�����Բ���ҪRememberMe���๦�ܣ�
 * </p>
 * <p>
 * Author: szj
 * </p>
 * <p>
 * Date: 2018��11��7��
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
