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
	
//	public void excuteUpdate(String query, PreparedStatementSetter pss) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			con = DBConnection.getConnection();
//			pstmt = con.prepareStatement(query);
//			
//			pss.setParameters(pstmt);
//			
//			pstmt.executeUpdate();
//
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
	public void excuteUpdate(String query, PreparedStatementSetter pss) {
		
		try ( Connection con = ConnectionPool.INSTANCE.getConnection();
			  PreparedStatement pstmt = con.prepareStatement(query); ) {
			pss.setParameters(pstmt);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public Object executeQuery(String query, PreparedStatementSetter pss, RowMapper rm) {
		Object vo = null;
		
		try ( Connection con = ConnectionPool.INSTANCE.getConnection();
			  PreparedStatement pstmt = con.prepareStatement(query); ) {
			
			pss.setParameters(pstmt);
			
			try ( ResultSet rs = pstmt.executeQuery(); ) {
				vo = rm.mapRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
		return vo;
	}
	
	public void excuteTransaction(String[] query, PreparedStatementSetter[] pss) throws SQLException {
		Connection con = null;
//		PreparedStatement pstmt = null;
		
		try {
			con = ConnectionPool.INSTANCE.getConnection();
			
			// transaction block start
			con.setAutoCommit(false);
			for(int i=0 ; i<query.length ; i++) {
				try (PreparedStatement pstmt = con.prepareStatement(query[i]); ) {
					pss[i].setParameters(pstmt);
					pstmt.executeUpdate();
				}
			}
			// transaction block end
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.setAutoCommit(true);
			try {
				if(con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
