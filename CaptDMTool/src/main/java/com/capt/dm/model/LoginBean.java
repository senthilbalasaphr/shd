package com.capt.dm.model;
import javax.validation.constraints.Size; 

public class LoginBean {

	@Size(min=1,message="required") 
	private String userName;
	@Size(min=1,message="required")
	private String password;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
