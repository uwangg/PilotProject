package com.zum.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {
//	private DBConnection dbConnection;
	
	public JdbcTemplate() {
		// TODO Auto-generated constructor stub
//		this.dbConnection = new MySQLConnection();
	}
	
	public void excuteUpdate(String query, PreparedStatementSetter pss) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBConnection.getConnection();
			pstmt = con.prepareStatement(query);
			
			pss.setParameters(pstmt);
			
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
	
	public Object executeQuery(String query, PreparedStatementSetter pss, RowMapper rm) {
		Object vo = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBConnection.getConnection();
			pstmt = con.prepareStatement(query);
			
			pss.setParameters(pstmt);

			rs = pstmt.executeQuery();
			vo = rm.mapRow(rs);
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
	
	public void excuteTransaction(String[] query, PreparedStatementSetter[] pss) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBConnection.getConnection();
			
			// transaction block start
			con.setAutoCommit(false);
			for(int i=0 ; i<query.length ; i++) {
				pstmt = con.prepareStatement(query[i]);
				pss[i].setParameters(pstmt);
				pstmt.executeUpdate();
			}
			// transaction block end
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.setAutoCommit(true);
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
