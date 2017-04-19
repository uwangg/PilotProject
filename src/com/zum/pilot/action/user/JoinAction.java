package com.zum.pilot.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.db.MySQLConnection;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.UserDao;
import com.zum.pilot.util.SecurityUtil;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.UserVo;

public class JoinAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		request.setCharacterEncoding("utf-8");
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String passwd = SecurityUtil.encryptSHA256(request.getParameter("passwd"));
		
		System.out.println("join: name="+name+",email="+email+",password="+passwd);
		
		UserDao userDao = new UserDao(new MySQLConnection());
		
		UserVo userVo = new UserVo(email, name, passwd);
		userDao.insert(userVo);
		WebUtil.redirect(request, response, "/pilot-project/user?a=joinsuccess");
	}

}
