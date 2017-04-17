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
import com.zum.pilot.vo.UserVo;

public class CommentDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		Long user_id = authUser.getId();
		Long post_id = Long.parseLong(request.getParameter("post_id"));
		Long comment_id = Long.parseLong(request.getParameter("id"));
		CommentDao commentDao = new CommentDao(new MySQLConnection());
		commentDao.delete(comment_id, user_id);
		
		WebUtil.redirect(request, response, "/pilot-project/board?a=view&id="+post_id);
	}

}
