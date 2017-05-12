package com.zum.pilot.vo;

public class CommentVo {
	private Long id;	// id
	private String content;	// 댓글 내용
	private String createTime;	// 작성일
	private String updateTime;	// 수정일
	private Integer thread;	// 댓글 순서
	private Integer depth;	// 답글 깊이
	private Long userId;	// 작성자 id
	private Long postId;	// 게시글 id
	private String userName;	// id로 찾은 유저이름
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
