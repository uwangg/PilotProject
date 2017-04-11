package com.zum.pilot.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.db.MySQLConnection;
import com.zum.pilot.SecurityUtil;
import com.zum.pilot.WebUtil;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.UserDao;
import com.zum.pilot.vo.UserVo;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null){
			System.out.println("로그인하지 않은 사용자");
			WebUtil.redirect(request, response, "/pilot-project/main");
			return;
		}
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String change_password = request.getParameter("change_password");
		String change_confirm = request.getParameter("change_confirm");
		System.out.println("name = " + name + ", password = " + password);
		if(password.equals("") || password == null) {
			System.out.println("패스워드 입력하지 않음");
			WebUtil.redirect(request, response, "/pilot-project/user?a=modifyform");
			return;
		}
		if(!change_password.equals(change_confirm)) {
			System.out.println("바꿀 패스워드 != 확인패스워드");
			WebUtil.redirect(request, response, "/pilot-project/user?a=modifyform");
			return;
		}
		
		// 회원 수정
		authUser.setName(name);
		authUser.setPassword(SecurityUtil.encryptSHA256(password));
		UserDao userDao = new UserDao(new MySQLConnection());
		userDao.update(authUser, SecurityUtil.encryptSHA256(change_password));

		// 세션 정보 변경
		authUser.setPassword("");
		session.setAttribute("authUser", authUser);
		
		System.out.println("바뀐 세션 = " + ((UserVo)session.getAttribute("authUser")).getName() + ", " + ((UserVo)session.getAttribute("authUser")).getPassword());
		
		WebUtil.redirect(request, response, "/pilot-project/main");
	}

}
