package com.shaozj.shiro.bean;

import com.shaozj.shiro.eums.ResultEnum;

public class ResultGenerator {
	
	private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
	
	private static final String DEFAULT_FAIL_MESSAGE = "FAIL";
	
	/**
	 * 
	 * <p>Description: ���سɹ����</p>
	 * @return
	 */
	public static <T> Result<T> genSuccessResult() {
        return new Result<T>()
                .setCode(ResultEnum.SUCCESS.code)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

	/**
	 * 
	 * <p>Description: �ɹ�����</p>
	 * @param data ������Ϣ
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
	 * <p>Description: �ɹ�����</p>
	 * @param code ���ش���
	 * @param data ������Ϣ
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
     * <p>Description: ���󷵻�</p>
     * @return
     */
    public static <T>  Result<T> genFailResult() {
        return new Result<T>()
                .setCode(ResultEnum.FAIL.code)
                .setMessage(DEFAULT_FAIL_MESSAGE);
    }
    
    /**
     * 
     * <p>Description: ���󷵻�</p>
     * @param message ������Ϣ
     * @return
     */
    public static <T>  Result<T> genFailResult(String message) {
        return new Result<T>()
                .setCode(ResultEnum.FAIL.code)
                .setMessage(message);
    }
    
    /**
     * 
     * <p>Description: ���󷵻�</p>
     * @param code �������
     * @param message ������Ϣ
     * @return
     */
    public static <T>  Result<T> genFailResult(Integer code,String message) {
        return new Result<T>()
                .setCode(code)
                .setMessage(message);
    }
}
