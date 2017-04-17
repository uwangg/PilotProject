package com.zum.pilot.action.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.db.MySQLConnection;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.UserDao;

public class CheckEmailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String email = request.getParameter("email");
		PrintWriter out = response.getWriter();
		
		System.out.println("check email = " + email);
		UserDao userDao = new UserDao(new MySQLConnection());
		
		// 이메일 중복체크
		if(userDao.checkEmail(email))
		{
			out.println("false");	// email 중복
		} else {
			out.println("true");
		}
	}

}
