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
 * Description: �������󶼻��Ⱦ���filter���˴��̳йٷ���BasicHttpAuthenticationFilter
 * 	�����ִ������ preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 * </p>
 * <p>
 * Author: szj
 * </p>
 * <p>
 * Date: 2018��11��7��
 * </p>
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	/**
	 *  ��дpreHandle����ӿ���֧��
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				httpServletRequest.getHeader("Access-Control-Request-Headers"));
		// ����ʱ�����ȷ���һ��option�����������Ǹ�option����ֱ�ӷ�������״̬
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}
	
	/**
	 * Ϊʲô���صĶ���true�����������
	 * ���������ṩһ����ַGET/article
	 * �����û����οͿ����������ǲ�ͨ��
	 * ��������ﷵ��false������ᱻ���أ��û��������κζ���
	 * �������������ﶼ����true��Controller�п���ͨ�� subject.isAuthenticated()���ж��û��Ƿ����
	 * �����Щ��Դֻ�е�¼�û����ܷ��ʣ�����ֻ��Ҫ�ڷ����������@RequiresAuthentication ע�⼴��
	 * ������������һ��ȱ�㣬���ǲ��ܹ���GET,POST��������зֱ���˼�Ȩ(��Ϊ������д�˹ٷ��ķ���)����ʵ���϶�Ӧ��Ӱ�첻��
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
	 * �ж��û��Ƿ���Ҫ��¼ ���header�����Ƿ����Authorization�ֶμ���
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
		// �ύ��realm���е��룬������������ܳ��쳣������
		getSubject(httpServletRequest, response).login(token);
		// ���û���ܳ��쳣,��������ɹ�������true
		return true;
	}


	/**
	 * �Ƿ�url��ת401
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
