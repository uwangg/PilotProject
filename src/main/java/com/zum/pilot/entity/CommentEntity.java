package com.zum.pilot.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class CommentEntity {
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
  private UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private PostEntity postEntity;

  public CommentEntity() {}

  public CommentEntity(String content, int depth, Long postId, Long userId) {
    this.postEntity = new PostEntity();
    postEntity.setId(postId);
    this.userEntity = new UserEntity();
    userEntity.setId(userId);
    this.content = content;
    this.depth = depth;
  }

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

  public UserEntity getUserEntity() {
    return userEntity;
  }

  public void setUserEntity(UserEntity userEntity) {
    this.userEntity = userEntity;
  }

  public PostEntity getPostEntity() {
    return postEntity;
  }

  public void setPostEntity(PostEntity postEntity) {
    this.postEntity = postEntity;
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
    return this.userEntity.getName();
  }
  public Long getUserId() {
    return this.userEntity.getId();
  }
}
