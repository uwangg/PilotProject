package com.zum.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class JdbcTemplate {
	private DBConnection dbConnection;
	
	public JdbcTemplate() {
		// TODO Auto-generated constructor stub
		this.dbConnection = new MySQLConnection();
	}
	
	public void excuteUpdate(String query) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dbConnection.getConnection();
			pstmt = con.prepareStatement(query);
			
			setParameters(pstmt);
			
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
	
	public Object executeQuery(String query) {
		Object vo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = dbConnection.getConnection();
			pstmt = con.prepareStatement(query);
			
			setParameters(pstmt);

			rs = pstmt.executeQuery();
			vo = mapRow(rs);
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
		
		return vo;
	}
	
	public abstract Object mapRow(ResultSet rs) throws SQLException;

	public abstract void setParameters(PreparedStatement pstmt) throws SQLException;
}
