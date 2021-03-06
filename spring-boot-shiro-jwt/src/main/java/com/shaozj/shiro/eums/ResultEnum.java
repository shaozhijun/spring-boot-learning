package com.shaozj.shiro.eums;

public enum ResultEnum {
	
	/**
	 * 成功
	 */
	SUCCESS(200),
	
	/**
	 * 失败
	 */
    FAIL(400),
    
    /**
     * 未认证（签名错误）
     */
    UNAUTHORIZED(401),
    
    /**
     * 接口不存在
     */
    NOT_FOUND(404),
    
    /**
     * 服务器内部错误
     */
    SERVER_ERROR(500);

    public int code;

    ResultEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
