package com.shaozj.shiro.bean;

import com.alibaba.fastjson.JSON;

public class Result<T> {
	
	private Integer code;
	
    private String message;
    
    private  T data;
    
    public Integer getCode() {
        return code;
    }

    public Result() {
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
