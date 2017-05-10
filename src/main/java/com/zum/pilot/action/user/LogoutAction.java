package com.zum.pilot.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.pilot.action.Action;
import com.zum.pilot.util.WebUtil;

public enum LogoutAction implements Action {
	INSTANCE;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		//로그아웃 처리
		session.removeAttribute("authUser");	// 세션 삭제
		session.invalidate();	// 세션 종료
		WebUtil.redirect(response, "/pilot-project/");
	}

}
