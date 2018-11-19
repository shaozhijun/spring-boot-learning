package com.shaozj.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import com.shaozj.shiro.jwt.JwtToken;
import com.shaozj.shiro.jwt.JwtUtil;
import com.shaozj.shiro.model.User;
import com.shaozj.shiro.service.IUserService;

/**
 * 
 * <p>ClassName: ShiroRealm</p>
 * <p>Description: �����֤��</p>
 * <p>Author: szj</p>
 * <p>Date: 2018��11��7��</p>
 */
public class ShiroRealm extends AuthorizingRealm {

	IUserService userService;
	
	/**
	 * ������д�˷�������Ȼshiro�ᱨ��
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}

	/**
	 * �����û�Ȩ��
	 * ֻ�е���Ҫ����û�Ȩ�޵�ʱ��Ż���ô˷���������hasRole��hesPermission֮���
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = JwtUtil.getUsername(principals.toString());
		
		User user = userService.findByUsername(username);
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRole(user.getRole());
		return simpleAuthorizationInfo;
	}

	/**
	 * ��֤�û��������Ƿ���ȷ
	 * Ĭ��ʹ�ô˷��������û��������֤�������׳��쳣����
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {

        String token = (String) authToken.getCredentials();
        String username = JwtUtil.getUsername(token);
		if (StringUtils.isEmpty(username)) {
			throw new AuthenticationException("token��Ч");
		}
		User user = userService.findByUsername(username);
		if (user == null) {
			throw new AuthenticationException("�û�������!");
		}
		boolean isTrue = JwtUtil.verify(token, username, user.getPassword());
		if (!isTrue) {
			throw new AuthenticationException("�û������������!");
		}
		return new SimpleAuthenticationInfo(token, token, "my_realm");
	}

}
