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
import com.zum.pilot.vo.UserVo;

public class CheckNameAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser != null) {
			if (authUser.getName().equals(name)) {
				out.print("true");
				return;
			}
		}

		System.out.println("check name = " + name);
		UserDao userDao = new UserDao(new MySQLConnection());

		// 닉네임 중복체크
		if (userDao.checkName(name)) {
			out.print("false"); // id 중복
		} else {
			out.print("true");
		}
	}

}