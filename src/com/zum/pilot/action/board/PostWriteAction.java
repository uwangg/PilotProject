package com.zum.pilot.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.db.MySQLConnection;
import com.zum.pilot.WebUtil;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.vo.PostVo;
import com.zum.pilot.vo.UserVo;

public class PostWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			System.out.println("로그인하지않아 글을 쓸수 없습니다.");
			WebUtil.redirect(request, response, "/pilot-project/main");
			return;
		}
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		PostVo postVo = new PostVo(title, content, authUser.getId());
		PostDao postDao = new PostDao(new MySQLConnection());
		postDao.insert(postVo);
		WebUtil.redirect(request, response, "/pilot-project/main");
	}

}
