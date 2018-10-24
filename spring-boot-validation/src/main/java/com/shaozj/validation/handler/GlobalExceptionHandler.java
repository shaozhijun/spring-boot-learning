package com.shaozj.validation.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(BindException.class)
	public String bindExceptionHandler(BindException e) {
		logger.info("");
		FieldError fieldError = e.getFieldError();
		StringBuilder sb = new StringBuilder();
		sb.append(fieldError.getField())
		  .append("=[")
		  .append(fieldError.getRejectedValue())
		  .append("]")
          .append(fieldError.getDefaultMessage());
		
		logger.error("error = {}", sb.toString());
		return sb.toString();
	}
}
