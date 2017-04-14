package com.zum.pilot.vo;

public class CommentVo {
	private Long id;	// id
	private String content;	// 댓글 내용
	private String create_time;	// 작성일
	private String update_time;	// 수정일
	private Integer thread;	// 댓글 순서
	private Integer depth;	// 답글 깊이
	private Long user_id;	// 작성자 id
	private Long post_id;	// 게시글 id
	private String user_name;	// id로 찾은 유저이름
	private Boolean delete_flag;
	
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
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getPost_id() {
		return post_id;
	}
	public void setPost_id(Long post_id) {
		this.post_id = post_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Boolean getDelete_flag() {
		return delete_flag;
	}
	public void setDelete_flag(Boolean delete_flag) {
		this.delete_flag = delete_flag;
	}
	
}
