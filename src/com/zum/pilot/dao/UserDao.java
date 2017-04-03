package com.zum.pilot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zum.db.DBConnection;
import com.zum.pilot.vo.UserVo;

public class UserDao {
	private DBConnection dbConnection;

	public UserDao(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}
	
	// id를 이용해 user 정보 가져오기
	public UserVo get(int id) {
		UserVo userVo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dbConnection.getConnection();
			String query = "select id, email, name from user where no=?";
			pstmt = con.prepareStatement(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userVo;
	}
	
	// 회원가입시
	public void insert(UserVo vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dbConnection.getConnection();
			String query = "insert into user(email, name, passwd) "
					+ "values(?,?,passsword(?))";
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getPassword());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
