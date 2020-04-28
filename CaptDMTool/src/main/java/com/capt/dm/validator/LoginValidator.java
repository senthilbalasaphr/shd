package com.capt.dm.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.capt.dm.model.LoginBean;

public class LoginValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return LoginBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub

		LoginBean login = (LoginBean) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "required.username");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.password");
		
		/*if("NONE".equals(cust.getCountry())){
			errors.rejectValue("country", "required.country");
		   }*/

	}

}