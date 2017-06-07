package com.zum.pilot.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post")
public class PostEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;    // 게시글 id

  @Column(name = "title", nullable = false)
  private String title;    // 게시글 제목
  @Column(name = "content")
  private String content;    // 게시글 내용
  @Column(name = "image_path")
  private String imagePath;    // 게시글 이미지 주소
  @Column(name = "create_time")
  private Date createTime;    // 작성일
  @Column(name = "update_time")
  private Date updateTime;    // 수정일
  @Column(name = "hit")
  private Long hit;    // 조회수
  
  @Column(name = "delete_flag")
  private boolean deleteFlag = false;

  @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", updatable=false)
  private UserEntity userEntity;

  public PostEntity() {
    this.userEntity = new UserEntity();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public Long getHit() {
    return hit;
  }

  public void setHit(Long hit) {
    this.hit = hit;
  }

  public UserEntity getUserEntity() {
    return userEntity;
  }

  public void setUserEntity(UserEntity userEntity) {
    this.userEntity = userEntity;
  }

  @PrePersist
  protected void onCreate() {
    updateTime = createTime = new Date();
    hit = 0L;
    deleteFlag = false;
    if(imagePath == null)
      imagePath = "";
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

  public Long getUserId() { return userEntity.getId(); }
  public String getUserName() {return userEntity.getName();}

  public boolean isDeleteFlag() {
    return deleteFlag;
  }

  public void setDeleteFlag(boolean deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  @Override
  public String toString() {
    return "PostEntity{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", imagePath='" + imagePath + '\'' +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", hit=" + hit +
            ", deleteFlag=" + deleteFlag +
            ", userEntity=" + userEntity +
            '}';
  }
}
