package com.zum.pilot.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.db.MySQLConnection;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.PostVo;
import com.zum.pilot.vo.UserVo;

public class PostModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		// 로그인하지 않은 사용자
		HttpSession session = request.getSession();
		
		if(request.getParameter("id") == null)
		{
			WebUtil.redirect(request, response, "/pilot-project/board");
			return;
		}
		
		Long id = Long.parseLong(request.getParameter("id"));
		UserVo authUser = (UserVo)session.getAttribute("authUser");

		PostDao postDao = new PostDao(new MySQLConnection());
		PostVo vo = postDao.get(id);
		
		// id값이 범위를 벗어날때
		if(vo == null) {
			WebUtil.redirect(request, response, "/pilot-project/board");
			return;
		}
		// 작성자와 로그인한 유저가 다를때
		if(vo.getUser_id() != authUser.getId()) {
			WebUtil.redirect(request, response, "/pilot-project/board");
			return;
		}
		request.setAttribute("vo", vo);
		WebUtil.forward(request, response, "/WEB-INF/views/board/modifyform.jsp");
	}

}
