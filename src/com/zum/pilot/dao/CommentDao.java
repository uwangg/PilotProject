package com.zum.pilot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.zum.db.DBConnection;
import com.zum.pilot.vo.CommentVo;

public class CommentDao {
	private DBConnection dbConnection;

	public CommentDao(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	// 댓글 입력
	public void insert(CommentVo vo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dbConnection.getConnection();

			String query = "insert into comment(content, thread, depth, user_id, post_id)" + " values(?,?,?,?,?)";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, vo.getContent());
			pstmt.setInt(2, vo.getThread());
			pstmt.setInt(3, vo.getDepth());
			pstmt.setLong(4, vo.getUser_id());
			pstmt.setLong(5, vo.getPost_id());

			int r = pstmt.executeUpdate();
			if (r == 1)
				System.out.println("댓글이 입력되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
