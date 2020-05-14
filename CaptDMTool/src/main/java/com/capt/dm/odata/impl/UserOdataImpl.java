package com.capt.dm.odata.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.capt.dm.odata.UserOdata;

public class UserOdataImpl implements UserOdata {
	
	DataSource dataSource ;
	 
    public DataSource getDataSource()
    {
        return this.dataSource;
    }
 
    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }
 
    @Override
    public boolean isValidUser(String userName, String password) throws SQLException
    {
        /*String query = "Select count(1) from user where username = ? and password = ?";
        PreparedStatement pstmt = dataSource.getConnection().prepareStatement(query);
        pstmt.setString(1, username);
        pstmt.setString(2, password);
        ResultSet resultSet = pstmt.executeQuery();
        if(resultSet.next())
            return (resultSet.getInt(1) > 0);
        else
           return false;
       }
*/
    	if (userName.equalsIgnoreCase("capt") && password.equalsIgnoreCase("Welcome1")) {
    		return true;
    	}   	
    	else if (userName.equalsIgnoreCase("user01") && password.equalsIgnoreCase("user01")) {
    		return true;
    	}
    	else if (userName.equalsIgnoreCase("user02") && password.equalsIgnoreCase("user02")) {
    		return true;
    	}
    	else if (userName.equalsIgnoreCase("user03") && password.equalsIgnoreCase("user03")) {
    		return true;
    	}
    	
    	else {
    		
    		
    		
    		
    		
    		return false;
    	}
    }
    
}
