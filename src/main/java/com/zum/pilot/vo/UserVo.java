package com.zum.pilot.vo;

public class UserVo {
  private Long id;    // 유저 id
  private String email;    // 유저 이메일
  private String name;    // 유저 닉네임
  private String password;    // 유저 비밀번호
  private String createTime;    // 가입일
  private String updatetime;    // 회원수정일

  public UserVo() {
  }

  public UserVo(String email, String name, String password) {
    super();
    this.email = email;
    this.name = name;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getUpdatetime() {
    return updatetime;
  }

  public void setUpdatetime(String updatetime) {
    this.updatetime = updatetime;
  }

}
