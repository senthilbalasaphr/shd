package com.capt.dm.delegate;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.capt.dm.service.UserService;
 
public class LoginDelegate
{
	@Autowired
    private UserService userService;
 
    public UserService getUserService()
    {
        return this.userService;
    }
 
    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }
 
    public boolean isValidUser(String username, String password) throws SQLException
    {
        return userService.isValidUser(username, password);
    }
}

