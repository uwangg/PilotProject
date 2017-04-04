package com.zum.pilot.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.db.MySQLConnection;
import com.zum.pilot.SecurityUtil;
import com.zum.pilot.WebUtil;
import com.zum.pilot.dao.UserDao;
import com.zum.pilot.vo.UserVo;

/**
 * Servlet implementation class JoinServlet
 */
@WebServlet("/join")
public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String password = SecurityUtil.encryptSHA256(request.getParameter("password"));
		
		System.out.println("request = " + email + ", " + name);
		
		UserVo userVo = new UserVo(email, name, password);
		UserDao userDao = new UserDao(new MySQLConnection());
		userDao.insert(userVo);
		
		WebUtil.redirect(request, response, "/pilot-project/main");
	}

}
