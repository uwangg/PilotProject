package com.zum.pilot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zum.db.DBConnection;
import com.zum.pilot.vo.PostVo;

public class PostDao {
	private DBConnection dbConnection;

	public PostDao(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}
	
	// 게시글 불러오기
	public List<PostVo> getList() {
		List<PostVo> list = new ArrayList<PostVo>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			con = dbConnection.getConnection();
	
			String query = "select a.id, title, a.create_time, hit, b.name "
					+ "from (select id, title, create_time, hit, user_id "
					+ "from post order by id asc limit 0,10) as a, user as b "
					+ "where a.user_id=b.id order by id desc";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String create_time = rs.getString(3);
				Long hit = rs.getLong(4);
				String user_name = rs.getString(5);

				PostVo postVo = new PostVo();
				postVo.setId(id);
				postVo.setTitle(title);
				postVo.setCreate_time(create_time);
				postVo.setHit(hit);
				postVo.setUser_name(user_name);
				list.add(postVo);
			}

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (con != null) {
					con.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
