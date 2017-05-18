package com.zum.pilot.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zum.db.JdbcTemplate;
import com.zum.db.PreparedStatementSetter;
import com.zum.db.RowMapper;
import com.zum.pilot.vo.CommentVo;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDao {

  // 댓글의 총 갯수
  public Long totalNumberOfComment(Long postId) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "select count(*) from comment where post_id=?";
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, postId);
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

  // 댓글 불러오기
  @SuppressWarnings("unchecked")
  public List<CommentVo> getList(Long postId, int currentPageNum, int commentUnit) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "select c.id, content, c.create_time, thread, depth, user_id, u.name, c.delete_flag "
            + "from (select * from comment where post_id=? order by thread asc limit ?,?) as c, user as u "
            + "where c.user_id = u.id order by thread desc";
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, postId);
        pstmt.setInt(2, currentPageNum);
        pstmt.setInt(3, commentUnit);
      }
    };
    RowMapper rm = new RowMapper() {

      @Override
      public Object mapRow(ResultSet rs) throws SQLException {
        List<CommentVo> list = new ArrayList<CommentVo>();
        while (rs.next()) {
          Long id = rs.getLong(1);
          String content = rs.getString(2);
          String createTime = rs.getString(3);
          Integer thread = rs.getInt(4);
          Integer depth = rs.getInt(5);
          Long userId = rs.getLong(6);
          String userName = rs.getString(7);
          Boolean deleteFlag = rs.getBoolean(8);

          CommentVo commentVo = new CommentVo();
          commentVo.setId(id);
          commentVo.setContent(content);
          commentVo.setCreateTime(createTime);
          commentVo.setThread(thread);
          commentVo.setDepth(depth);
          commentVo.setUserId(userId);
          commentVo.setUserName(userName);
          commentVo.setDeleteFlag(deleteFlag);
          list.add(commentVo);
        }
        return list;
      }
    };
    return (List<CommentVo>) template.executeQuery(query, pss, rm);
  }

  // 댓글 입력
  public void insert(CommentVo vo) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "insert into comment(content, thread, depth, user_id, post_id)" + " values(?,?,?,?,?)";
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, vo.getContent());
        pstmt.setInt(2, vo.getThread());
        pstmt.setInt(3, vo.getDepth());
        pstmt.setLong(4, vo.getUserId());
        pstmt.setLong(5, vo.getPostId());
      }
    };
    template.excuteUpdate(query, pss);
  }

  // 게시글에 달린 댓글의 갯수를 알아오기
  public Long countOfComment(Long postId) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "select count(*) from comment where post_id=?";
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, postId);
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

  // 최대 thread값 가져오기
  public int getMaxThread(Long postId) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "select max(thread) from comment where post_id=?";
    PreparedStatementSetter pss = new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, postId);
      }
    };
    RowMapper rm = new RowMapper() {

      @Override
      public Object mapRow(ResultSet rs) throws SQLException {
        int thread = 0;
        if (rs.next()) {
          thread = rs.getInt(1);
        }
        return thread;
      }
    };
    return (int) template.executeQuery(query, pss, rm);
  }

  public void updateThread(int begin, int end) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "update comment set thread = thread-1 where ?<thread and thread<?";
    PreparedStatementSetter pss = new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setInt(1, begin);
        pstmt.setInt(2, end);
      }
    };
    template.excuteUpdate(query, pss);
  }

  // 댓글 업데이트
  public void update(CommentVo vo) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "update comment set content=?, update_time=now() where id=? and user_id=?";
    PreparedStatementSetter pss = new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, vo.getContent());
        pstmt.setLong(2, vo.getId());
        pstmt.setLong(3, vo.getUserId());
      }
    };
    template.excuteUpdate(query, pss);
  }

  // 댓글 삭제
  public void delete(Long commentId, Long userId) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "update comment set delete_flag=1 " + "where id=? and user_id=?";
    PreparedStatementSetter pss = new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, commentId);
        pstmt.setLong(2, userId);
      }
    };
    template.excuteUpdate(query, pss);
  }
}
