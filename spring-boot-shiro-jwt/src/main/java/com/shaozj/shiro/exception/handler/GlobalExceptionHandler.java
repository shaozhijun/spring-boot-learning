package com.shaozj.shiro.exception.handler;

import org.apache.shiro.ShiroException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.shaozj.shiro.exception.ShiroAuthException;

import org.springframework.http.HttpStatus;

public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(ShiroException.class)
	public String handle401(ShiroException e) {
		return null;
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(JWTDecodeException.class)
	public String decodeException(JWTDecodeException e) {
		return null;
	}

	/**
	 * 
	 * <p>
	 * Description: shiro鉴权异常处理
	 * </p>
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ShiroAuthException.class)
	public String shiroAuthException(ShiroAuthException e) {
		return null;
	}
}
