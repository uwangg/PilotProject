package com.zum.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection implements DBConnection {

	@Override
	public Connection getConnection() throws SQLException {
		Connection con = null;
		
		try {
			// 1단계 - jdbc 드라이버 Load
			Class.forName("com.mysql.jdbc.Driver");
			
			// 2단계 - Connection 객체 생성
//			String url = "jdbc:mysql://localhost/pilot?autoReconnect=true&useSSL=false";
			String url = "jdbc:mysql://localhost/pilot";
			con = DriverManager.getConnection(url, "pilot", "pilot");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다." + e);
		}
		
		return con;
	}

}
