package com.zum.pilot.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.pilot.action.Action;
import com.zum.pilot.dao.CommentDao;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.CommentVo;
import com.zum.pilot.vo.PostVo;

public enum PostViewAction implements Action {
	INSTANCE;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 게시글 id를 이용해 게시물 불러오기
		Long postId = Long.parseLong(request.getParameter("id"));
		PostDao postDao = PostDao.INSTANCE;
		postDao.hitIncrease(postId);
		PostVo postVo = postDao.get(postId);
		
		
		// 댓글 페이지네이션
		Long totalCommentNum = 0L;	// 게시글의 총 갯수
		int totalPageNum = 0; 	// 총 페이지 번호의 수
		int commentUnit = 10;	// 한 페이지당 보여줄 글의 최대 갯수
		int pageNumUnit = 5;	// 한 페이지 블락당 보여줄 번호의 최대 갯수
		int currentPageNum = 1;	// 눌린 페이지 번호
		int begin = 1, end = 1;
		
		
		// 게시물 id에 맞는 댓글 불러오기
		List<CommentVo> commentList = null;
		CommentDao commentDao = CommentDao.INSTANCE;
		
		totalCommentNum = commentDao.totalNumberOfComment(postId);
		totalPageNum = (int)((totalCommentNum - 1) / commentUnit + 1);
		System.out.println("댓글수 : " + totalCommentNum + ", 페이지번호수 : "+totalPageNum);
		
		// 현재 눌린 페이지 가져오기
		if(request.getParameter("currentPageNum") == null) {
			currentPageNum = 1;
		} else {
			currentPageNum = Integer.parseInt(request.getParameter("currentPageNum"));
		}
		commentList = commentDao.getList(postId, (totalPageNum-currentPageNum)*commentUnit, commentUnit);
		
		
		// 시작과 끝페이지 가져오기
		if(request.getParameter("begin") == null) {
			begin = 1;
		} else {
			begin = Integer.parseInt(request.getParameter("begin"));
		}
		end = (begin-1) + pageNumUnit;
		if(end > totalPageNum)
			end = totalPageNum;		
		
		
		request.setAttribute("postVo", postVo);
		request.setAttribute("commentList", commentList);
		request.setAttribute("begin", begin);
		request.setAttribute("end", end);
		request.setAttribute("totalPageNum", totalPageNum);
		request.setAttribute("currentPageNum", currentPageNum);
		request.setAttribute("pageNumUnit", pageNumUnit);
		
		WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
	}

}
