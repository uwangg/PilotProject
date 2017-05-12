package com.zum.pilot.vo;

public class PostVo {
	private Long id;	// 게시글 id
	private String title;	// 게시글 제목
	private String content;	// 게시글 내용
	private String imagePath;	// 게시글 이미지 주소
	private String createTime;	// 작성일
	private String updateTime;	// 수정일
	private Long hit;	// 조회수
	private Long userId;	// 작성자 id
	private String userName;	// id로 찾은 유저이름
	
	
	public PostVo() {
		this.title = "";
		this.content = "";
		this.imagePath = "";
	}
	public PostVo(String title, Long userId) {
		super();
		this.title = title;
		this.userId = userId;
		this.content = "";
		this.imagePath = "";
	}
	public PostVo(String title, String content, Long userId) {
		super();
		this.title = title;
		this.content = content;
		this.userId = userId;
		this.imagePath = "";
	}
	public PostVo(String title, String content, String imagePath, Long userId) {
		super();
		this.title = title;
		this.content = content;
		this.imagePath = imagePath;
		this.userId = userId;
	}
	public PostVo(Long id, String title, String content, String imagePath, Long userId) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.imagePath = imagePath;
		this.userId = userId;
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
	public Long getHit() {
		return hit;
	}
	public void setHit(Long hit) {
		this.hit = hit;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
