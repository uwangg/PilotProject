package com.zum.pilot.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
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
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "create_time", nullable = false)
  private Date createTime;    // 가입일
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "update_time", nullable = false)
  @UpdateTimestamp
  private Date updateTime;    // 회원수정일
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

  public boolean isDeleteFlag() {
    return deleteFlag;
  }

  public void setDeleteFlag(boolean deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  @PrePersist
  protected void onCreate() {
    updateTime = createTime = new Date();
  }
  @PreUpdate
  protected void onUpdate() {
    updateTime = new Date();
  }

  public Date getCreateTime() {
    return createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }
}
