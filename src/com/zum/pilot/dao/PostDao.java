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
	
	// 게시글의 총 갯수
	public Long totalNumberOfPost() {
		Long totalCount = 0L;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			con = dbConnection.getConnection();

			String query = "select count(*) from post where delete_flag=0";
			pstmt = con.prepareStatement(query);
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
	

	// 게시글 불러오기
	public List<PostVo> getList(int currentPageNum, int postUnit) {
		List<PostVo> list = new ArrayList<PostVo>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			con = dbConnection.getConnection();

			String query = "select a.id, title, a.create_time, hit, b.name "
					+ "from (select id, title, create_time, hit, user_id "
					+ "from post where delete_flag=0 order by id asc limit ?,?) as a, user as b "
					+ "where a.user_id=b.id order by id desc";
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, currentPageNum);
			pstmt.setInt(2, postUnit);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String createTime = rs.getString(3);
				Long hit = rs.getLong(4);
				String userName = rs.getString(5);

				PostVo postVo = new PostVo();
				postVo.setId(id);
				postVo.setTitle(title);
				postVo.setCreateTime(createTime);
				postVo.setHit(hit);
				postVo.setUserName(userName);
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

	// 게시글 입력
	public void insert(PostVo vo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = dbConnection.getConnection();
			
			String query = "insert into post(title, content, image_path, user_id)"
					+ " values(?,?,?,?)";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getImagePath());
			pstmt.setLong(4, vo.getUserId());

			int r = pstmt.executeUpdate();
			if(r == 1)
				System.out.println("게시글이 입력되었습니다.");
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
	
	// 게시물 세부정보 가져오기
		public PostVo get(Long number) {
			PostVo postVo = null;
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				con = dbConnection.getConnection();
				String query = "select a.id, title, content, image_path, a.create_time, user_id, name "
						+ "from (select * from post where id=?)as a, user as u "
						+ "where a.user_id=u.id";
				pstmt = con.prepareStatement(query);
				
				pstmt.setLong(1, number);

				rs = pstmt.executeQuery();
				while(rs.next()) {
					Long id = rs.getLong("id");
					String title = rs.getString("title");
					String content = rs.getString("content");
					String imagePath = rs.getString("image_path");
					String createTime = rs.getString("create_time");
					Long userId = rs.getLong("user_id");
					String userName = rs.getString("name");
					
					postVo = new PostVo();
					postVo.setId(id);
					postVo.setTitle(title);
					postVo.setContent(content);
					postVo.setImagePath(imagePath);
					postVo.setCreateTime(createTime);
					postVo.setUserId(userId);
					postVo.setUserName(userName);
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
			
			return postVo;
		}
		
		// 글 수정시
		public void update(PostVo vo) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = dbConnection.getConnection();
					String query = "update post set title=?, content=?, image_path=?, update_time=now() "
							+ "where id=? and user_id=?";
					pstmt = con.prepareStatement(query);

					pstmt.setString(1, vo.getTitle());
					pstmt.setString(2, vo.getContent());
					pstmt.setString(3, vo.getImagePath());
					pstmt.setLong(4, vo.getId());
					pstmt.setLong(5, vo.getUserId());
				
				int r = pstmt.executeUpdate();
				if(r == 1)
					System.out.println("글이 수정 되었습니다.");
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
		
		// 게시글 삭제
		public void delete(Long postId, Long userId) throws SQLException {
			Connection con = null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			
			String query1 = "update post set delete_flag=1 where id=? and user_id=?";
			String query2 = "update comment set delete_flag=1 where post_id=?";
			try {
				con = dbConnection.getConnection();

				// transcaction block start
				con.setAutoCommit(false);
				pstmt1 = con.prepareStatement(query1);
				pstmt1.setLong(1, postId);
				pstmt1.setLong(2, userId);
				pstmt1.executeUpdate();
			
				pstmt2 = con.prepareStatement(query2);
				pstmt2.setLong(1, postId);
				pstmt2.executeUpdate();
				
				// transaction block end
				con.commit();
				
			} catch (SQLException e) {
				e.printStackTrace();
				con.rollback();
			} finally {
				con.setAutoCommit(true);
				try {
					if(pstmt1 != null)
						pstmt1.close();
					if(pstmt2 != null)
						pstmt2.close();
					if(con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		// 조회수 증가
		public void hitIncrease(Long number) {
			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = dbConnection.getConnection();

				String sql = "update post set hit=hit+1 where id=?";
				pstmt = conn.prepareStatement(sql);

				pstmt.setLong(1, number);

				pstmt.executeUpdate();

			} catch (SQLException e) {
				System.out.println("error : " + e);
			} finally {
				try {
					if (pstmt != null) {
						pstmt.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
}
