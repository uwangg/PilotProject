package com.zum.pilot.action.user;

import java.io.IOException;
import java.io.PrintWriter;

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

public class WithdrawalAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		
		if(session == null){
			System.out.println("로그인하지 않은 사용자");
			WebUtil.redirect(request, response, "/pilot-project/main");
			return;
		}
		
		// db에서 회원정보 삭제
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		String password = SecurityUtil.encryptSHA256(request.getParameter("passwd"));

		UserDao userDao = new UserDao(new MySQLConnection());
		int result = userDao.delete(userVo.getId(), password);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if(result == 0) {
			System.out.println("비밀번호 틀림");
			out.println("<script language=\"javascript\">");
			out.println("alert('비밀번호가 틀렸습니다.'); location.href=\"/pilot-project/user?a=withdrawalform\"");
			out.println("</script>");
			out.close();
			return;
		}
		
		//로그아웃 처리
		session.removeAttribute("authUser");	// 세션 삭제
		session.invalidate();	// 세션 종료
		
//		WebUtil.redirect(request, response, "/pilot-project/main");
		out.println("<script language=\"javascript\">");
		out.println("alert('회원탈퇴가 완료되었습니다.'); location.href=\"/pilot-project/main\"");
		out.println("</script>");
		out.close();
	}

}
