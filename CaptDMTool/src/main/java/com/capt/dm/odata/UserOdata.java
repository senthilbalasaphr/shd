package com.capt.dm.odata;

import java.sql.SQLException;

/**
 * This interface will be used to communicate with the Database
 */
public interface UserOdata {
	public boolean isValidUser(String username, String password) throws SQLException;
}