package com.zum.pilot.action.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.pilot.action.Action;
import com.zum.pilot.dao.UserDao;
import com.zum.pilot.util.SecurityUtil;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.UserVo;

public enum ModifyAction implements Action {
	INSTANCE;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		String name = request.getParameter("name");
		String password = request.getParameter("passwd");
		String changePassword = request.getParameter("change_passwd");
		String changeConfirm = request.getParameter("change_confirm");

		// 새 비밀번호 != 비밀번호 확인
		if(!changePassword.equals(changeConfirm)) {
			System.out.println("새비밀번호와 일치하지않음");
			WebUtil.redirect(request, response, "/pilot-project/user?a=modifyform");
			return;
		}
		
		UserDao userDao = UserDao.INSTANCE;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if(!userDao.checkPassword(authUser.getId(), SecurityUtil.encryptSHA256(password))) {
			out.println("<script language=\"javascript\">");
			out.println("alert('비밀번호가 틀렸습니다.'); location.href=\"/pilot-project/user?a=modifyform\"");
			out.println("</script>");
			out.close();
			return;
		} 
		
		// 회원 수정
		authUser.setName(name);
		authUser.setPassword(SecurityUtil.encryptSHA256(password));
		
		if(changePassword.equals("") || changePassword == null) {
			userDao.update(authUser, "");
		} else {
			userDao.update(authUser, SecurityUtil.encryptSHA256(changePassword));
		}

		// 세션 정보 변경
		authUser.setPassword("");
		session.setAttribute("authUser", authUser);
		
		System.out.println("바뀐 세션 = " + ((UserVo)session.getAttribute("authUser")).getName() + ", " + ((UserVo)session.getAttribute("authUser")).getPassword());
		
		WebUtil.redirect(request, response, "/pilot-project/main");
	}

}
