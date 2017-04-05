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
			String query = "select id, email, name from user where id=? and passwd=?";
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, vo.getEmail());	// 첫번째  ?에 id값
			pstmt.setString(2, vo.getPassword());
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
	
	// 회원가입시 중복성체크
	public boolean checkName(String name) {
		boolean check = true;	// name이 존재하면 true
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dbConnection.getConnection();
			String query = "select count(*) from user where name=?";
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, name);	// 첫번째  ?에 id값
			rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt(1) == 0)
				check = false;
			
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
		
		return check;
	}
	public boolean checkEmail(String email) {
		boolean check = true;	// name이 존재하면 true
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dbConnection.getConnection();
			String query = "select count(*) from user where email=?";
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, email);	// 첫번째  ?에 id값
			rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt(1) == 0)
				check = false;
			
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
		
		return check;
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
	
	// 회원수정시
	public void update(UserVo vo, String newPassword) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dbConnection.getConnection();
			if (newPassword != "") {	// 패스워드 변경시
				String query = "update user set name=? password=? where id=? and passwd=?";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1, vo.getName());
				pstmt.setString(2, newPassword);
				pstmt.setLong(3, vo.getId());
				pstmt.setString(4, vo.getPassword());
			} else {
				String query = "update user set name=? where id=? and passwd=?";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1, vo.getName());
				pstmt.setLong(2, vo.getId());
				pstmt.setString(3, vo.getPassword());
			}
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
	
	// 회원탈퇴
	public void delete(UserVo vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dbConnection.getConnection();
			String query = "delete from user where id=? and passwd=?";
			pstmt = con.prepareStatement(query);
			
			pstmt.setLong(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			
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
