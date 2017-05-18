package com.zum.pilot.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zum.db.JdbcTemplate;
import com.zum.db.PreparedStatementSetter;
import com.zum.db.RowMapper;
import com.zum.pilot.vo.PostVo;
import org.springframework.stereotype.Repository;

@Repository
public class PostDao {
//  INSTANCE;

  // 게시글의 총 갯수
  public Long totalNumberOfPost() {
    JdbcTemplate template = new JdbcTemplate();
    String query = "select count(*) from post where delete_flag=0";
    PreparedStatementSetter pss = new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
      }
    };
    RowMapper rm = new RowMapper() {
      @Override
      public Object mapRow(ResultSet rs) throws SQLException {
        Long totalCount = 0L;
        if (rs.next()) {
          totalCount = rs.getLong(1);
        }
        return totalCount;
      }
    };
    return (Long) template.executeQuery(query, pss, rm);
  }

  // 게시글 불러오기
  @SuppressWarnings("unchecked")
  public List<PostVo> getList(int currentPageNum, int postUnit) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "select a.id, title, a.create_time, hit, b.name "
            + "from (select id, title, create_time, hit, user_id "
            + "from post where delete_flag=0 order by id asc limit ?,?) as a, user as b "
            + "where a.user_id=b.id order by id desc";
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, currentPageNum);
        pstmt.setInt(2, postUnit);
      }
    };
    RowMapper rm = new RowMapper() {

      @Override
      public Object mapRow(ResultSet rs) throws SQLException {
        List<PostVo> list = new ArrayList<PostVo>();
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
        return list;
      }
    };
    return (List<PostVo>) template.executeQuery(query, pss, rm);
  }

  // 게시글 입력
  public void insert(PostVo vo) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "insert into post(title, content, image_path, user_id)" + " values(?,?,?,?)";
    PreparedStatementSetter pss = new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, vo.getTitle());
        pstmt.setString(2, vo.getContent());
        pstmt.setString(3, vo.getImagePath());
        pstmt.setLong(4, vo.getUserId());
      }
    };
    template.excuteUpdate(query, pss);
  }

  // 게시물 세부정보 가져오기
  public PostVo get(Long number) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "select a.id, title, content, image_path, a.create_time, user_id, name "
            + "from (select * from post where id=? and delete_flag = 0)as a, user as u where a.user_id=u.id";
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, number);
      }
    };
    RowMapper rm = new RowMapper() {

      @Override
      public Object mapRow(ResultSet rs) throws SQLException {
        PostVo postVo = null;
        while (rs.next()) {
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
        return postVo;
      }
    };
    return (PostVo) template.executeQuery(query, pss, rm);
  }

  // 글 수정시
  public void update(PostVo vo) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "update post set title=?, content=?, image_path=?, update_time=now() "
            + "where id=? and user_id=?";
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, vo.getTitle());
        pstmt.setString(2, vo.getContent());
        pstmt.setString(3, vo.getImagePath());
        pstmt.setLong(4, vo.getId());
        pstmt.setLong(5, vo.getUserId());
      }
    };
    template.excuteUpdate(query, pss);
  }

  // 게시글 삭제
  public void delete(Long postId, Long userId) throws SQLException {
    JdbcTemplate template = new JdbcTemplate();

    String[] query = new String[2];
    query[0] = "update post set delete_flag=1 where id=? and user_id=?";
    query[1] = "update comment set delete_flag=1 where post_id=?";

    PreparedStatementSetter[] pss = new PreparedStatementSetter[2];
    pss[0] = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, postId);
        pstmt.setLong(2, userId);
      }
    };
    pss[1] = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, postId);
      }
    };
    template.excuteTransaction(query, pss);
  }

  // 조회수 증가
  public void hitIncrease(Long number) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "update post set hit=hit+1 where id=?";
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, number);
      }
    };
    template.excuteUpdate(query, pss);
  }
}
