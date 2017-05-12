package com.zum.pilot.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zum.db.JdbcTemplate;
import com.zum.db.PreparedStatementSetter;
import com.zum.db.RowMapper;
import com.zum.pilot.vo.UserVo;

public enum UserDao {
  INSTANCE;

  // 회원인증시
  public UserVo get(UserVo vo) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "select id, email, name from user where email = ? and passwd=? and delete_flag=0";
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, vo.getEmail());    // 첫번째  ?에 id값
        pstmt.setString(2, vo.getPassword());
      }
    };
    RowMapper rm = new RowMapper() {

      @Override
      public Object mapRow(ResultSet rs) throws SQLException {
        UserVo userVo = null;
        while (rs.next()) {
          Long id = rs.getLong("id");
          String email = rs.getString("email");
          String name = rs.getString("name");

          userVo = new UserVo();
          userVo.setId(id);
          userVo.setEmail(email);
          userVo.setName(name);
        }
        return userVo;
      }
    };
    return (UserVo) template.executeQuery(query, pss, rm);
  }

  // 회원가입시 중복성체크
  // 닉네임 중복체크
  public boolean checkName(String name) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "select count(*) from user where name=? and delete_flag=0";
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, name);    // 첫번째  ?에 id값
      }
    };
    RowMapper rm = new RowMapper() {

      @Override
      public Object mapRow(ResultSet rs) throws SQLException {
        boolean check = true;    // name이 존재하면 true
        rs.next();
        if (rs.getInt(1) == 0)
          check = false;
        return check;
      }
    };
    return (boolean) template.executeQuery(query, pss, rm);
  }

  // 이메일 중복체크
  public boolean checkEmail(String email) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "select count(*) from user where email=? and delete_flag=0";
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, email);    // 첫번째  ?에 id값
      }
    };
    RowMapper rm = new RowMapper() {

      @Override
      public Object mapRow(ResultSet rs) throws SQLException {
        boolean check = true;    // name이 존재하면 true
        rs.next();
        if (rs.getInt(1) == 0)
          check = false;
        return check;
      }
    };
    return (Boolean) template.executeQuery(query, pss, rm);
  }

  // 패스워드 체크
  public boolean checkPassword(Long id, String password) {
    JdbcTemplate template = new JdbcTemplate();
    String query = "select count(*) from user where id=? and passwd=? and delete_flag=0";
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, id);    // 첫번째  ?에 id값
        pstmt.setString(2, password);
      }
    };
    RowMapper rm = new RowMapper() {

      @Override
      public Object mapRow(ResultSet rs) throws SQLException {
        boolean check = true;    // name이 존재하면 true
        rs.next();
        if (rs.getInt(1) == 0)
          check = false;
        return check;
      }
    };
    return (boolean) template.executeQuery(query, pss, rm);
  }

  // 회원가입시
  public void insert(UserVo vo) {
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, vo.getEmail());
        pstmt.setString(2, vo.getName());
        pstmt.setString(3, vo.getPassword());
      }
    };
    JdbcTemplate template = new JdbcTemplate() {
    };
    String query = "insert into user(email, name, passwd) values(?,?,?)";
    template.excuteUpdate(query, pss);
  }


  // 회원수정시
  public int update(UserVo vo, String newPassword) {
    int result = 0;
    PreparedStatementSetter pss = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        if (newPassword != "") {    // 패스워드 변경시
          pstmt.setString(1, vo.getName());
          pstmt.setString(2, newPassword);
          pstmt.setLong(3, vo.getId());
          pstmt.setString(4, vo.getPassword());
        } else {
          pstmt.setString(1, vo.getName());
          pstmt.setLong(2, vo.getId());
          pstmt.setString(3, vo.getPassword());
        }
      }
    };
    JdbcTemplate template = new JdbcTemplate() {
    };
    String query = null;
    if (newPassword != "") {
      query = "update user set name=?, passwd=? where id=? and passwd=?";
    } else {
      query = "update user set name=? where id=? and passwd=?";
    }
    template.excuteUpdate(query, pss);
    return result;
  }

  // 회원탈퇴
  public void delete(Long id, String password) throws SQLException {
    JdbcTemplate template = new JdbcTemplate();

    String[] query = new String[3];
    query[0] = "update user set delete_flag=1 where id=? and passwd=?";
    query[1] = "update post set delete_flag=1 where user_id=?";
    query[2] = "update comment set delete_flag=1 where user_id=?";

    PreparedStatementSetter[] pss = new PreparedStatementSetter[3];
    pss[0] = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, id);
        pstmt.setString(2, password);
      }
    };
    pss[1] = new PreparedStatementSetter() {
      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, id);
      }
    };
    pss[2] = new PreparedStatementSetter() {

      @Override
      public void setParameters(PreparedStatement pstmt) throws SQLException {
        pstmt.setLong(1, id);
      }
    };
    template.excuteTransaction(query, pss);
  }
}
