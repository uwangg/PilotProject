package com.zum.pilot.action.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.db.MySQLConnection;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.UserDao;
import com.zum.pilot.util.SecurityUtil;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.UserVo;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
//		if(authUser == null){
//			System.out.println("로그인하지 않은 사용자");
//			WebUtil.redirect(request, response, "/pilot-project/main");
//			return;
//		}
		
		String name = request.getParameter("name");
		String password = request.getParameter("passwd");
		String change_password = request.getParameter("change_passwd");
		
		UserDao userDao = new UserDao(new MySQLConnection());
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
		
		if(change_password.equals("") || change_password == null) {
			userDao.update(authUser, "");
		} else {
			userDao.update(authUser, SecurityUtil.encryptSHA256(change_password));
		}

		// 세션 정보 변경
		authUser.setPassword("");
		session.setAttribute("authUser", authUser);
		
		System.out.println("바뀐 세션 = " + ((UserVo)session.getAttribute("authUser")).getName() + ", " + ((UserVo)session.getAttribute("authUser")).getPassword());
		
		WebUtil.redirect(request, response, "/pilot-project/main");
	}

}
