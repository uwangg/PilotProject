package com.zum.pilot.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.pilot.action.Action;
import com.zum.pilot.dao.CommentDao;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.CommentVo;
import com.zum.pilot.vo.UserVo;

public enum CommentWriteAction implements Action {
	INSTANCE;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final int thrUnit = 1000;
		Long postId = Long.parseLong(request.getParameter("postId"));
		int depth = Integer.parseInt(request.getParameter("depth"));
		String content = request.getParameter("content");
		
		System.out.println("댓글 내용 : "+content);
		// 댓글 작성자 정보 가져오기
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		Long userId = authUser.getId();
		
		CommentVo commentVo = new CommentVo();
		commentVo.setPostId(postId);
		commentVo.setUserId(userId);
		commentVo.setContent(content);
		commentVo.setDepth(depth);
		
		CommentDao commentDao = CommentDao.INSTANCE;
		int thread = 0;
		
		if(depth == 0) {	// 댓글을 다는 경우
			thread = commentDao.getMaxThread(postId);
			thread = (thread/thrUnit) * thrUnit + thrUnit;
			System.out.println("thread = " + thread);
			commentVo.setThread(thread);
		} else {	// 답글을 다는 경우
			thread = Integer.parseInt(request.getParameter("thread"));
			commentVo.setThread(thread);
			int precommentThread = (thread/thrUnit) * thrUnit;
			commentDao.updateThread(precommentThread, thread+1);
		}
		
		commentDao.insert(commentVo);
		WebUtil.redirect(response, "/pilot-project/board?action=view&id="+postId);
	}

}
