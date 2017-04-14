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
			String query = "select id, email, name from user where no=? and delete_flag=0";
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
			String query = "select id, email, name from user where email=? and passwd=? and delete_flag=0";
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
			String query = "select count(*) from user where name=? and delete_flag=0";
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
			String query = "select count(*) from user where email=? and delete_flag=0";
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
			System.out.println("유저정보가 입력되었습니다.");
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
	public int update(UserVo vo, String newPassword) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			con = dbConnection.getConnection();
			if (newPassword != "") {	// 패스워드 변경시
				String query = "update user set name=?, passwd=? where id=? and passwd=?";
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
			result = pstmt.executeUpdate();
			if(result == 1)
				System.out.println("유저정보가 업데이트 되었습니다.");
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
		return result;
	}
	
	// 회원탈퇴
	public int delete(Long id, String password) {
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		int result = 0;
		
		try {
			con = dbConnection.getConnection();

//			String query = "update user as u, post as p, comment as c "
//					+ "set u.delete_flag=1, p.delete_flag=1, c.delete_flag=1 "
//					+ "where u.id=? and p.user_id=? and c.user_id=?";
			String query1 = "update user set delete_flag=1 where id=? and passwd=?";
			pstmt1 = con.prepareStatement(query1);
			pstmt1.setLong(1, id);
			pstmt1.setString(2, password);
			result += pstmt1.executeUpdate();
			
			if(result == 1) {
				String query2 = "update post set delete_flag=1 where user_id=?";
				pstmt2 = con.prepareStatement(query2);
				pstmt2.setLong(1, id);
				result += pstmt2.executeUpdate();
				
				String query3 = "update comment set delete_flag=1 where user_id=?";
				pstmt3 = con.prepareStatement(query3);
				pstmt3.setLong(1, id);
				result += pstmt3.executeUpdate();
			}
			
			if(result == 3)
				System.out.println("회원탈퇴 완료");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt1 != null)
					pstmt1.close();
				if(pstmt2 != null)
					pstmt2.close();
				if(pstmt3 != null)
					pstmt3.close();
				if(con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
//	public void delete(UserVo vo) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			con = dbConnection.getConnection();
//			String query = "delete from user where id=? and passwd=?";
//			pstmt = con.prepareStatement(query);
//			
//			pstmt.setLong(1, vo.getId());
//			pstmt.setString(2, vo.getPassword());
//			
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(pstmt != null)
//					pstmt.close();
//				if(con != null)
//					con.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
