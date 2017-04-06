package com.zum.pilot.action.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.db.MySQLConnection;
import com.zum.pilot.WebUtil;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.vo.PostVo;

public class DefaultAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<PostVo> postList = null;
		
		PostDao postDao = new PostDao(new MySQLConnection());
		postList = postDao.getList();
		
		request.setAttribute("postList", postList);
		WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
	}

}
