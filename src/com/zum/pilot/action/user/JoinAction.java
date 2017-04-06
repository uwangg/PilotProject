package com.zum.pilot.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.db.MySQLConnection;
import com.zum.pilot.SecurityUtil;
import com.zum.pilot.WebUtil;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.UserDao;
import com.zum.pilot.vo.UserVo;

public class JoinAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = SecurityUtil.encryptSHA256(request.getParameter("password"));
		String confirmPassword = SecurityUtil.encryptSHA256(request.getParameter("confirm"));
		
		System.out.println("join: name="+name+",email="+email+",password="+password);
		
		UserDao userDao = new UserDao(new MySQLConnection());
		
		if(name.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("")) {
			WebUtil.redirect(request, response, "/pilot-project/user?a=joinform");
			System.out.println("빈칸존재");
			return;
		}
		
		// 닉네임 중복체크
		if(userDao.checkName(name))
		{
			WebUtil.redirect(request, response, "/pilot-project/user?a=joinform");
			System.out.println("닉네임 중복");
			return;
		}
		// 이메일 중복체크
		if(userDao.checkEmail(email))
		{
			WebUtil.redirect(request, response, "/pilot-project/user?a=joinform");
			System.out.println("이메일 중복");
			return;
		}
		
		UserVo userVo = new UserVo(email, name, password);
		userDao.insert(userVo);
		WebUtil.redirect(request, response, "/pilot-project/user?a=joinsuccess");
	}

}
