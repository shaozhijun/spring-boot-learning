package com.shaozj.validation.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.shaozj.validation.annotation.Mobile;

public class User {

//	private Integer id;
	
	@Size(min = 2, max = 30)
	private String name;
	
	@Email
	private String email;
	
	@NotNull
	@Min(18)
	@Max(1000)
	private Integer age;
	
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past //验证注解的元素值(日期类型)，比当前时间早
	private Date birthday;
	
	@Mobile
	private String mobile;
}
