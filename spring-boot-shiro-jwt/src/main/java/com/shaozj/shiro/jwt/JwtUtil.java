package com.shaozj.shiro.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * 
 * <p>
 * ClassName: JwtUtil
 * </p>
 * <p>
 * Description: JWT加密、校验工具类，使用用户自己的密码充当加密密钥，这样保证了token及时被其他人接货也无法破解
 * 并且在token中附带了username信息，设置密钥5分钟就会过期
 * 
 * </p>
 * <p>
 * Author: szj
 * </p>
 * <p>
 * Date: 2018年11月7日
 * </p>
 */
public class JwtUtil {

	private static final long EXPIRE_TIME = 5 * 60 * 1000;

	private static final String JWT_KEY = "username";

	/**
	 * 
	 * <p>
	 * Description: 校验token是否正确
	 * </p>
	 * 
	 * @param token
	 *            密钥
	 * @param secret
	 *            用户的密码
	 * @return 是否正确
	 */
	public static boolean verify(String token, String username, String password) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(password);
			JWTVerifier verifier = JWT.require(algorithm).withClaim(JWT_KEY, username).build();
			verifier.verify(token);
			return true;
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * <p>
	 * Description: 获取token中的信息，无需secret解密也可以获得
	 * </p>
	 * 
	 * @param token
	 * @return
	 */
	public static String getUsername(String token) {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getClaim(JWT_KEY).asString();
	}

	/**
	 * 
	 * <p>
	 * Description: 生成签名，过期时间：5分钟
	 * </p>
	 * 
	 * @param username
	 *            用户名
	 * @param seccret
	 *            用户的密码
	 * @return
	 */
	public static String sign(String username, String password) {
		try {
			Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
			Algorithm algorithm = Algorithm.HMAC256(password);
			return JWT.create().withClaim(JWT_KEY, username).withExpiresAt(date).sign(algorithm);
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			return null;
		}
	}

}
