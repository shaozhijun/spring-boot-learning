package com.shaozj.validation.annotation.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.shaozj.validation.annotation.Mobile;

/**
 * 手机号校验规则
 * @author szj
 *
 */
public class MobileValidator implements ConstraintValidator<Mobile, String> {

	private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$"
    );
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		if ( value == null || value.length() == 0 ) {
            return true;
        }
        Matcher m = PHONE_PATTERN.matcher(value);
        return m.matches();
	}

}
