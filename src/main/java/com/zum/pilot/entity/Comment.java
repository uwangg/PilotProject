package com.zum.pilot.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;    // id
  private String content;    // 댓글 내용
  @Column(name = "create_time")
  private Date createTime;    // 작성일
  @Column(name = "update_time")
  private Date updateTime;    // 수정일
  private Integer thread;    // 댓글 순서
  private Integer depth;    // 답글 깊이

  @Column(name = "delete_flag")
  private Boolean deleteFlag;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getThread() {
    return thread;
  }

  public void setThread(Integer thread) {
    this.thread = thread;
  }

  public Integer getDepth() {
    return depth;
  }

  public void setDepth(Integer depth) {
    this.depth = depth;
  }

  public Boolean getDeleteFlag() {
    return deleteFlag;
  }

  public void setDeleteFlag(Boolean deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }

  @PrePersist
  protected void onCreate() {
    updateTime = createTime = new Date();
    deleteFlag = false;
    if(content == null)
      content = "";
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

  public String getUserName() {
    return this.user.getName();
  }
  public Long getUserId() {
    return this.user.getId();
  }
}
