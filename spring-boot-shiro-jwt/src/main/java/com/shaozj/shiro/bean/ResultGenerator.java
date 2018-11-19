package com.shaozj.shiro.bean;

import com.shaozj.shiro.eums.ResultEnum;

public class ResultGenerator {
	
	private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
	
	private static final String DEFAULT_FAIL_MESSAGE = "FAIL";
	
	/**
	 * 
	 * <p>Description: 返回成功结果</p>
	 * @return
	 */
	public static <T> Result<T> genSuccessResult() {
        return new Result<T>()
                .setCode(ResultEnum.SUCCESS.code)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

	/**
	 * 
	 * <p>Description: 成功返回</p>
	 * @param data 返回信息
	 * @return
	 */
	public static <T>  Result<T> genSuccessResult(T data) {
        return new Result<T>()
                .setCode(ResultEnum.SUCCESS.code)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }
	
	/**
	 * 
	 * <p>Description: 成功返回</p>
	 * @param code 返回代码
	 * @param data 返回信息
	 * @return
	 */
    public static <T> Result<T> genSuccessResult(Integer code, T data) {
        return new Result<T>()
                .setCode(code)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }
    
    /**
     * 
     * <p>Description: 错误返回</p>
     * @return
     */
    public static <T>  Result<T> genFailResult() {
        return new Result<T>()
                .setCode(ResultEnum.FAIL.code)
                .setMessage(DEFAULT_FAIL_MESSAGE);
    }
    
    /**
     * 
     * <p>Description: 错误返回</p>
     * @param message 错误信息
     * @return
     */
    public static <T>  Result<T> genFailResult(String message) {
        return new Result<T>()
                .setCode(ResultEnum.FAIL.code)
                .setMessage(message);
    }
    
    /**
     * 
     * <p>Description: 错误返回</p>
     * @param code 错误代码
     * @param message 错误信息
     * @return
     */
    public static <T>  Result<T> genFailResult(Integer code,String message) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message);
    }
}
