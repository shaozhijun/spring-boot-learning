package com.shaozj.async.exception;

public class AsyncException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7158294234065721229L;

	public AsyncException(String msg) {
        super(msg);
    }
    
    public AsyncException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
