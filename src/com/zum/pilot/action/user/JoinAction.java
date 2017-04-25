package com.zum.pilot.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.pilot.action.Action;
import com.zum.pilot.dao.UserDao;
import com.zum.pilot.util.SecurityUtil;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.UserVo;

public enum JoinAction implements Action {
	INSTANCE;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		request.setCharacterEncoding("utf-8");
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String passwd = request.getParameter("passwd");
		String confirm = request.getParameter("confirm");
		
		if(!passwd.equals(confirm)) {
			System.out.println("패스워드 != 패스워드확인");
			WebUtil.redirect(request, response, "/pilot-project/user?a=joinform");
			return;
		}
		
		System.out.println("join: name="+name+",email="+email+",password="+passwd);
		
//		UserDao userDao = new UserDao(new MySQLConnection());
//		UserDao userDao = new UserDao();
		UserDao userDao = UserDao.INSTANCE;
		
		UserVo userVo = new UserVo(email, name, SecurityUtil.encryptSHA256(passwd));
		userDao.insert(userVo);
		WebUtil.redirect(request, response, "/pilot-project/user?a=joinsuccess");
	}

}
