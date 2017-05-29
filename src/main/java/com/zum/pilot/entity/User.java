package com.zum.pilot.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user",
        uniqueConstraints = {
          @UniqueConstraint(columnNames = {"email", "name"})
        }
)
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;    // 유저 id
  @Column(name = "email", nullable = false)
  private String email;    // 유저 이메일
  @Column(name = "name", nullable = false)
  private String name;    // 유저 닉네임

  @Column(name = "passwd")
  private String password;    // 유저 비밀번호
  @Column(name = "create_time")
  private String createTime;    // 가입일
  @Column(name = "update_time")
  private String updateTime;    // 회원수정일
  @Column(name = "delete_flag")
  private boolean deleteFlag;

//  private List<Post> posts;
//  private List<Comment> comments;

//  @OneToMany(mappedBy = "Post", cascade = CascadeType.ALL)

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

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public boolean isDeleteFlag() {
    return deleteFlag;
  }

  public void setDeleteFlag(boolean deleteFlag) {
    this.deleteFlag = deleteFlag;
  }
}
