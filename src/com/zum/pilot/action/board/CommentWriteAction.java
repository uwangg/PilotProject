package com.zum.pilot.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.db.MySQLConnection;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.CommentDao;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.CommentVo;
import com.zum.pilot.vo.UserVo;

public class CommentWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
		
//		Long countOfComment = 0L;
		int thr_unit = 1000;
		Long post_id = Long.parseLong(request.getParameter("post_id"));
		int depth = Integer.parseInt(request.getParameter("depth"));
		String content = request.getParameter("content");
		
		// 댓글 작성자 정보 가져오기
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		Long user_id = authUser.getId();
		
		CommentVo commentVo = new CommentVo();
		commentVo.setPost_id(post_id);
		commentVo.setUser_id(user_id);
		commentVo.setContent(content);
		commentVo.setDepth(depth);
		
		CommentDao commentDao = new CommentDao(new MySQLConnection());
		int thread = 0;
		
		if(depth == 0) {	// 댓글을 다는 경우
			thread = commentDao.getMaxThread(post_id);
			thread = (thread/thr_unit) * thr_unit + thr_unit;
			System.out.println("thread = " + thread);
			commentVo.setThread(thread);
		} else {	// 답글을 다는 경우
			thread = Integer.parseInt(request.getParameter("thread"));
			commentVo.setThread(thread);
			int precomment_thread = (thread/thr_unit) * thr_unit;
			commentDao.updateThread(precomment_thread, thread+1);
		}
		
		commentDao.insert(commentVo);
		WebUtil.redirect(request, response, "/pilot-project/board?a=view&id="+post_id);
	}

}
