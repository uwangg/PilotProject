package com.zum.pilot.vo;

public class PostVo {
	private Long id;	// 게시글 id
	private String title;	// 게시글 제목
	private String content;	// 게시글 내용
	private String image_path;	// 게시글 이미지 주소
	private String create_time;	// 작성일
	private String update_time;	// 수정일
	private Long hit;	// 조회수
	private Long user_id;	// 작성자 id
	private String user_name;	// id로 찾은 유저이름
	
	
	public PostVo() {
		this.title = "";
		this.content = "";
		this.image_path = "";
	}
	public PostVo(String title, Long user_id) {
		super();
		this.title = title;
		this.user_id = user_id;
		this.content = "";
		this.image_path = "";
	}
	public PostVo(String title, String content, Long user_id) {
		super();
		this.title = title;
		this.content = content;
		this.user_id = user_id;
		this.image_path = "";
	}
	public PostVo(String title, String content, String image_path, Long user_id) {
		super();
		this.title = title;
		this.content = content;
		this.image_path = image_path;
		this.user_id = user_id;
	}
	public PostVo(Long id, String title, String content, String image_path, Long user_id) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.image_path = image_path;
		this.user_id = user_id;
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
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public Long getHit() {
		return hit;
	}
	public void setHit(Long hit) {
		this.hit = hit;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
}
