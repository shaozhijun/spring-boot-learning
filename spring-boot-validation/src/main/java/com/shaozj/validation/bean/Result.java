package com.shaozj.validation.bean;

import java.util.HashMap;

public class Result extends HashMap<String, Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3481096448575542596L;
	
	public Result error(String code, String message) {
		Result result = new Result();
		result.put("code", code);
		result.put("message", message);
		return result;
	}
	
	
	public Result success(String code, String message) {
		Result result = new Result();
		result.put("code", code);
		result.put("message", message);
		return result;
	}

}
