package com.zum.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public enum ConnectionPool {
	INSTANCE;
	
	private DataSource dataSource;
	
	private ConnectionPool() {
    	// JNDI 사용
		try {
			Context context = new InitialContext();
	        dataSource = (DataSource) context.lookup("java:comp/env/jdbc/PilotDB");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public Connection getConnection() throws SQLException{
		return dataSource.getConnection();
    }
}
