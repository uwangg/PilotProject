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

public class PostModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		if(session == null) {
			WebUtil.redirect(request, response, "/pilot-project/board");
			return;
		}
		
		Long id = Long.parseLong(request.getParameter("id"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String image_path = request.getParameter("image_path");
		Long user_id = Long.parseLong(request.getParameter("user_id"));
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser.getId() == user_id) {
			PostVo vo = new PostVo();
			vo.setId(id);
			vo.setTitle(title);
			vo.setContent(content);
			vo.setImage_path(image_path);
			vo.setUser_id(user_id);
		
			PostDao postDao = new PostDao(new MySQLConnection());
			postDao.update(vo);
		}
		
		WebUtil.redirect(request, response, "/pilot-project/board?a=view?id="+user_id);
	}

}
