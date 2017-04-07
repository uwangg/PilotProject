package com.zum.pilot.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.db.MySQLConnection;
import com.zum.pilot.WebUtil;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.vo.PostVo;

public class PostViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		Long id = Long.parseLong(request.getParameter("id"));
		PostDao postDao = new PostDao(new MySQLConnection());
		PostVo vo = postDao.get(id);
		request.setAttribute("vo", vo);
		
		WebUtil.forward(request, response, "/WEB-INF/views/board/view.jsp");
	}

}
