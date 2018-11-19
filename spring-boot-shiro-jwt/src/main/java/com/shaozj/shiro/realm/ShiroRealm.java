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
 * <p>Description: 身份验证类</p>
 * <p>Author: szj</p>
 * <p>Date: 2018年11月7日</p>
 */
public class ShiroRealm extends AuthorizingRealm {

	IUserService userService;
	
	/**
	 * 必须重写此方法，不然shiro会报错
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}

	/**
	 * 检验用户权限
	 * 只有当需要检测用户权限的时候才会调用此方法，例如hasRole、hesPermission之类的
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
	 * 验证用户名密码是否正确
	 * 默认使用此方法进行用户的身份认证，错误抛出异常即可
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {

        String token = (String) authToken.getCredentials();
        String username = JwtUtil.getUsername(token);
		if (StringUtils.isEmpty(username)) {
			throw new AuthenticationException("token无效");
		}
		User user = userService.findByUsername(username);
		if (user == null) {
			throw new AuthenticationException("用户不存在!");
		}
		boolean isTrue = JwtUtil.verify(token, username, user.getPassword());
		if (!isTrue) {
			throw new AuthenticationException("用户名或密码错误!");
		}
		return new SimpleAuthenticationInfo(token, token, "my_realm");
	}

}
