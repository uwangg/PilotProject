package com.zum.pilot.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.db.MySQLConnection;
import com.zum.pilot.WebUtil;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.CommentDao;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.vo.CommentVo;
import com.zum.pilot.vo.PostVo;

public class PostViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		// 게시글 id를 이용해 게시물 불러오기
		Long post_id = Long.parseLong(request.getParameter("id"));
		PostDao postDao = new PostDao(new MySQLConnection());
		postDao.hitIncrease(post_id);
		PostVo postVo = postDao.get(post_id);
		
		// 게시물 id에 맞는 댓글 불러오기
		List<CommentVo> commentList = null;
		CommentDao commentDao = new CommentDao(new MySQLConnection());
		commentList = commentDao.getList(post_id);
		
		
		request.setAttribute("postVo", postVo);
		request.setAttribute("commentList", commentList);
		
		WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
	}

}
