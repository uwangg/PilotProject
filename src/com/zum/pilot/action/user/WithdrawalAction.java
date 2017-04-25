package com.zum.pilot.action.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.pilot.action.Action;
import com.zum.pilot.dao.UserDao;
import com.zum.pilot.util.SecurityUtil;
import com.zum.pilot.vo.UserVo;

public enum WithdrawalAction implements Action {
	INSTANCE;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();

		
		// db에서 회원정보 삭제
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		String password = SecurityUtil.encryptSHA256(request.getParameter("passwd"));

//		UserDao userDao = new UserDao(new MySQLConnection());
//		UserDao userDao = new UserDao();
		UserDao userDao = UserDao.INSTANCE;

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// id, password가 동일한지 체크
		if(!userDao.checkPassword(userVo.getId(), password)) {
			System.out.println("비밀번호 틀림");
			out.println("<script language=\"javascript\">");
			out.println("alert('비밀번호가 틀렸습니다.'); location.href=\"/pilot-project/user?a=withdrawalform\"");
			out.println("</script>");
			out.close();
			return;
		}
		
		// 동일하다면 삭제
		try {
			userDao.delete(userVo.getId(), password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//로그아웃 처리
		session.removeAttribute("authUser");	// 세션 삭제
		session.invalidate();	// 세션 종료
		
		out.println("<script language=\"javascript\">");
		out.println("alert('회원탈퇴가 완료되었습니다.'); location.href=\"/pilot-project/main\"");
		out.println("</script>");
		out.close();
	}

}
