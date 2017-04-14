package com.zum.pilot.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zum.db.DBConnection;
import com.zum.pilot.vo.CommentVo;

public class CommentDao {
	private DBConnection dbConnection;

	public CommentDao(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	// 댓글 불러오기
	public List<CommentVo> getList(Long post_id) {
		List<CommentVo> list = new ArrayList<CommentVo>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			con = dbConnection.getConnection();

			String query = "select c.id, content, c.create_time, thread, depth, user_id, u.name "
					+ "from (select * from comment where post_id=? and delete_flag=0) as c, user as u "
					+ "where c.user_id = u.id order by thread desc";
			pstmt = con.prepareStatement(query);

			pstmt.setLong(1, post_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong(1);
				String content = rs.getString(2);
				String create_time = rs.getString(3);
				Integer thread = rs.getInt(4);
				Integer depth = rs.getInt(5);
				Long user_id = rs.getLong(6);
				String user_name = rs.getString(7);

				CommentVo commentVo = new CommentVo();
				commentVo.setId(id);
				commentVo.setContent(content);
				commentVo.setCreate_time(create_time);
				commentVo.setThread(thread);
				commentVo.setDepth(depth);
				commentVo.setUser_id(user_id);
				commentVo.setUser_name(user_name);
				list.add(commentVo);
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

	// 게시글에 달린 댓글의 갯수를 알아오기
	public Long countOfComment(Long post_id) {
		Long totalCount = 0L;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			con = dbConnection.getConnection();

			String query = "select count(*) from comment where post_id=?";
			pstmt = con.prepareStatement(query);

			pstmt.setLong(1, post_id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				totalCount = rs.getLong(1);
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
		return totalCount;
	}

	// 최대 thread값 가져오기
	public int getMaxThread(Long post_id) {
		int thread = 0;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			con = dbConnection.getConnection();

			String query = "select max(thread) from comment where delete_flag=0 and post_id=?";
			pstmt = con.prepareStatement(query);
			pstmt.setLong(1, post_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				thread = rs.getInt(1);
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
		return thread;
	}
	
	public void updateThread(int begin, int end) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dbConnection.getConnection();
				String query = "update comment set thread = thread-1 where ?<thread and thread<? and delete_flag=0";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1, begin);
				pstmt.setInt(2, end);
			
			int r = pstmt.executeUpdate();
			if(r == 1)
				System.out.println("답글이 업데이트 되었습니다.");
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
	
	// 댓글 업데이트
	public void update(CommentVo vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = dbConnection.getConnection();
				String query = "update comment set content=?, update_time=now() where id=? and user_id=?";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1, vo.getContent());
				pstmt.setLong(2, vo.getId());
				pstmt.setLong(3, vo.getUser_id());
			
			int r = pstmt.executeUpdate();
			if(r == 1)
				System.out.println("댓글이 수정 되었습니다.");
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
