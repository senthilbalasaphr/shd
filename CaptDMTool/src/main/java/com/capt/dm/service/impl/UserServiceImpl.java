package com.capt.dm.service.impl;

import java.sql.SQLException;

import com.capt.dm.odata.UserOdata;
import com.capt.dm.service.UserService;
 
public class UserServiceImpl implements UserService
{
 
    private UserOdata userOdata;
 
    public UserOdata getUserOdata()
    {
        return this.userOdata;
    }
 
    public void setUserOdata(UserOdata userOdata)
    {
        this.userOdata = userOdata;
    }
 
    @Override
    public boolean isValidUser(String username, String password) throws SQLException
    {
        return userOdata.isValidUser(username, password);
    }
}
