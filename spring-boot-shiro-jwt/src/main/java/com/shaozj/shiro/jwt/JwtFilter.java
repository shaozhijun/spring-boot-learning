package com.shaozj.shiro.jwt;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * <p>
 * ClassName: JwtFilter
 * </p>
 * <p>
 * Description: 所有请求都会先经过filter，此处继承官方的BasicHttpAuthenticationFilter
 * 	代码的执行流程 preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 * </p>
 * <p>
 * Author: szj
 * </p>
 * <p>
 * Date: 2018年11月7日
 * </p>
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	/**
	 *  重写preHandle，添加跨域支持
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				httpServletRequest.getHeader("Access-Control-Request-Headers"));
		// 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}
	
	/**
	 * 为什么返回的都是true，即允许访问
	 * 例如我们提供一个地址GET/article
	 * 登入用户和游客看到的内容是不通的
	 * 如果在这里返回false，请求会被拦截，用户看不到任何东西
	 * 所以我们在这里都返回true，Controller中可以通过 subject.isAuthenticated()来判断用户是否登入
	 * 如果有些资源只有登录用户才能访问，我们只需要在方法上面加上@RequiresAuthentication 注解即可
	 * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginAttempt(request, response)) {
			executeLogin(request, response);
		} else {
			response401(request, response);
		}
		return true;
	}

	
	/**
	 * 判断用户是否想要登录 检测header里面是否包含Authorization字段即可
	 */
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		String authorization = req.getHeader("Authorization");
		if (authorization != null) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String authorization = httpServletRequest.getHeader("Authorization");
		JwtToken token = new JwtToken(authorization);
		// 提交给realm进行登入，如果错误它会跑出异常并补获
		getSubject(httpServletRequest, response).login(token);
		// 如果没有跑出异常,则代表登入成功，返回true
		return true;
	}


	/**
	 * 非法url跳转401
	 */
	private void response401(ServletRequest request, ServletResponse response) {
		try {
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			httpServletResponse.sendRedirect("/401");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

}
