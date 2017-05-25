package com.zum.pilot.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class CommentVo {
  @Id
  private Long id;    // id
  private String content;    // 댓글 내용
  @Column(name = "create_time")
  private String createTime;    // 작성일
  @Column(name = "update_time")
  private String updateTime;    // 수정일
  private Integer thread;    // 댓글 순서
  private Integer depth;    // 답글 깊이
  @Column(name = "user_id")
  private Long userId;    // 작성자 id
  @Column(name = "post_id")
  private Long postId;    // 게시글 id
  private String userName;    // id로 찾은 유저이름
  @Column(name = "delete_flag")
  private Boolean deleteFlag;

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

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Boolean getDeleteFlag() {
    return deleteFlag;
  }

  public void setDeleteFlag(Boolean deleteFlag) {
    this.deleteFlag = deleteFlag;
  }


}
