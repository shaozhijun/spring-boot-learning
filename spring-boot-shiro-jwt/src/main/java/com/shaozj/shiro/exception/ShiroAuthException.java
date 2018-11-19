package com.shaozj.shiro.exception;

public class ShiroAuthException extends RuntimeException {

	/**
	 * <p>Field serialVersionUID: TODO</p>
	 */
	private static final long serialVersionUID = 1106723733704908008L;
	
	public ShiroAuthException(String msg) {
        super(msg);
    }
    
    public ShiroAuthException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
