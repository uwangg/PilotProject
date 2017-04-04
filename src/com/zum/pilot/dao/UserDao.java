package com.zum.pilot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zum.db.DBConnection;
import com.zum.pilot.SecurityUtil;
import com.zum.pilot.vo.UserVo;

public class UserDao {
	private DBConnection dbConnection;

	public UserDao(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}
	
	// id를 이용해 user 정보 가져오기
	public UserVo get(Long number) {

		UserVo userVo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dbConnection.getConnection();
			String query = "select id, email, name from user where no=?";
			pstmt = con.prepareStatement(query);
			
			pstmt.setLong(1, number);	// 첫번째  ?에 id값
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long id = rs.getLong("id");
				String email = rs.getString("email");
				String name = rs.getString("name");
				
				userVo = new UserVo();
				userVo.setId(id);
				userVo.setEmail(email);
				userVo.setName(name);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null)
					con.close();
				if(pstmt != null)
					pstmt.close();
				if(rs != null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return userVo;
	}
	
	// 회원인증시
	public UserVo get(UserVo vo) {
		UserVo userVo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dbConnection.getConnection();
			String query = "select id, email, name from user where no=? and passwd=?";
			pstmt = con.prepareStatement(query);
			
			pstmt.setLong(1, vo.getId());	// 첫번째  ?에 id값
			pstmt.setString(2, SecurityUtil.encryptSHA256(vo.getPassword()));
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long id = rs.getLong("id");
				String email = rs.getString("email");
				String name = rs.getString("name");
				
				userVo = new UserVo();
				userVo.setId(id);
				userVo.setEmail(email);
				userVo.setName(name);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null)
					con.close();
				if(pstmt != null)
					pstmt.close();
				if(rs != null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
					+ "values(?,?,?)";
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, SecurityUtil.encryptSHA256(vo.getPassword()));
			
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
