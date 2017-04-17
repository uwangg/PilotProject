package com.zum.pilot.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.pilot.action.Action;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.UserVo;

public class PostWriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		
		if(userVo == null) {	// 로그인하지 않은 사용자가 글을 쓰려고 할 때
			System.out.println("로그인 하지 않은 사용자");
			WebUtil.redirect(request, response, "/pilot-project/main");
			return;
		}
		
		WebUtil.forward(request, response, "/WEB-INF/views/board/writeform.jsp");
	}

}
