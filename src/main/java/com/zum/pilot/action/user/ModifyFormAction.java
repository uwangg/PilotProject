package com.zum.pilot.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.pilot.action.Action;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.UserVo;

public enum ModifyFormAction implements Action {
	INSTANCE;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");	
	
		request.setAttribute("name", authUser.getName());
		
		WebUtil.forward(request, response, "/WEB-INF/views/user/modifyform.jsp");
	}

}
