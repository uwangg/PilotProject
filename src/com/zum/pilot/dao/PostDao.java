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

			String query = "select count(*) from post";
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
					+ "from post order by id asc limit ?,?) as a, user as b "
					+ "where a.user_id=b.id order by id desc";
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, currentPageNum);
			pstmt.setInt(2, postUnit);
			
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
			pstmt.setString(3, vo.getImage_path());
			pstmt.setLong(4, vo.getUser_id());

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
						+ "where a.user_id=u.id;";
				pstmt = con.prepareStatement(query);
				
				pstmt.setLong(1, number);

				rs = pstmt.executeQuery();
				while(rs.next()) {
					Long id = rs.getLong("id");
					String title = rs.getString("title");
					String content = rs.getString("content");
					String image_path = rs.getString("image_path");
					String create_time = rs.getString("create_time");
					Long user_id = rs.getLong("user_id");
					String user_name = rs.getString("name");
					
					postVo = new PostVo();
					postVo.setId(id);
					postVo.setTitle(title);
					postVo.setContent(content);
					postVo.setImage_path(image_path);
					postVo.setCreate_time(create_time);
					postVo.setUser_id(user_id);
					postVo.setUser_name(user_name);
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
					pstmt.setString(3, vo.getImage_path());
					pstmt.setLong(4, vo.getId());
					pstmt.setLong(5, vo.getUser_id());
				
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
		public void delete(Long id) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = dbConnection.getConnection();
				String query = "delete from post where id=?";
				pstmt = con.prepareStatement(query);
				
				pstmt.setLong(1, id);
				
				int r = pstmt.executeUpdate();
				if(r == 1)
					System.out.println("글이 삭제되었습니다.");
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
