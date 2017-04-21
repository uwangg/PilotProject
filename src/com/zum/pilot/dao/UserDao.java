package com.zum.pilot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zum.db.DBConnection;
import com.zum.db.JdbcTemplate;
import com.zum.db.PreparedStatementSetter;
import com.zum.db.RowMapper;
import com.zum.pilot.vo.UserVo;

public class UserDao {
	private DBConnection dbConnection;

	public UserDao() {}
	public UserDao(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}
	
	// id를 이용해 user 정보 가져오기
	public UserVo get(Long number) {
		JdbcTemplate template = new JdbcTemplate();
		String query = "select id, email, name from user where no=? and delete_flag=0";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setLong(1, number);	// 첫번째  ?에 id값
			}
		};
		RowMapper rm = new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				UserVo userVo = null;
				while(rs.next()) {
					Long id = rs.getLong("id");
					String email = rs.getString("email");
					String name = rs.getString("name");
					
					userVo = new UserVo();
					userVo.setId(id);
					userVo.setEmail(email);
					userVo.setName(name);
				}
				return userVo;
			}
		};
		return (UserVo)template.executeQuery(query, pss, rm);
	}
	
	// 회원인증시
	public UserVo get(UserVo vo) {		
		JdbcTemplate template = new JdbcTemplate();
		String query = "select id, email, name from user where email=? and passwd=? and delete_flag=0";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, vo.getEmail());	// 첫번째  ?에 id값
				pstmt.setString(2, vo.getPassword());
			}
		};
		RowMapper rm = new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				UserVo userVo = null;
				while(rs.next()) {
					Long id = rs.getLong("id");
					String email = rs.getString("email");
					String name = rs.getString("name");
					
					userVo = new UserVo();
					userVo.setId(id);
					userVo.setEmail(email);
					userVo.setName(name);
				}
				return userVo;
			}
		};
		return (UserVo)template.executeQuery(query, pss, rm);
	}
	
	// 회원가입시 중복성체크
	// 닉네임 중복체크
	public boolean checkName(String name) {
		JdbcTemplate template = new JdbcTemplate();
		String query = "select count(*) from user where name=? and delete_flag=0";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {				
				pstmt.setString(1, name);	// 첫번째  ?에 id값
			}
		};
		RowMapper rm = new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				boolean check = true;	// name이 존재하면 true
				rs.next();
				if(rs.getInt(1) == 0)
					check = false;
				return check;
			}
		};
		return (boolean)template.executeQuery(query, pss, rm);
	}
	
	// 이메일 중복체크
	public boolean checkEmail(String email) {
		JdbcTemplate template = new JdbcTemplate();
		String query = "select count(*) from user where email=? and delete_flag=0";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, email);	// 첫번째  ?에 id값
			}
		};
		RowMapper rm = new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				boolean check = true;	// name이 존재하면 true
				rs.next();
				if(rs.getInt(1) == 0)
					check = false;
				return check;
			}
		};
		return (Boolean)template.executeQuery(query, pss, rm);
	}
	
	// 패스워드 체크
	public boolean checkPassword(Long id, String password) {
		JdbcTemplate template = new JdbcTemplate();
		String query = "select count(*) from user where id=? and passwd=? and delete_flag=0";
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {				
				pstmt.setLong(1, id);	// 첫번째  ?에 id값
				pstmt.setString(2, password);
			}
		};
		RowMapper rm = new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs) throws SQLException {
				boolean check = true;	// name이 존재하면 true
				rs.next();
				if(rs.getInt(1) == 0)
					check = false;
				return check;
			}
		};
		return (boolean)template.executeQuery(query, pss, rm);
	}
	
	// 회원가입시
	public void insert(UserVo vo) {
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, vo.getEmail());
				pstmt.setString(2, vo.getName());
				pstmt.setString(3, vo.getPassword());
			}
		};
		JdbcTemplate template = new JdbcTemplate() {		
		};
		String query = "insert into user(email, name, passwd) values(?,?,?)";
		template.excuteUpdate(query, pss);
	}
	
	
	// 회원수정시
	public int update(UserVo vo, String newPassword) {
		int result = 0;
		PreparedStatementSetter pss = new PreparedStatementSetter() {
			
			@Override
			public void setParameters(PreparedStatement pstmt) throws SQLException {
				if (newPassword != "") {	// 패스워드 변경시
					pstmt.setString(1, vo.getName());
					pstmt.setString(2, newPassword);
					pstmt.setLong(3, vo.getId());
					pstmt.setString(4, vo.getPassword());
				} else {
					pstmt.setString(1, vo.getName());
					pstmt.setLong(2, vo.getId());
					pstmt.setString(3, vo.getPassword());
				}
			}
		};
		JdbcTemplate template = new JdbcTemplate() {
		};
		String query = null;
		if(newPassword != "") {
			query = "update user set name=?, passwd=? where id=? and passwd=?";
		} else {
			query = "update user set name=? where id=? and passwd=?";
		}
		template.excuteUpdate(query, pss);
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

}
