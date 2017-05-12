package com.zum.pilot.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.pilot.action.Action;
import com.zum.pilot.dao.UserDao;
import com.zum.pilot.util.SecurityUtil;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.UserVo;

public enum LoginAction implements Action {
  INSTANCE;

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String email = request.getParameter("email");
    String password = SecurityUtil.encryptSHA256(request.getParameter("password"));

    UserVo userVo = new UserVo();
    userVo.setEmail(email);
    userVo.setPassword(password);

    if (email.equals("") || password.equals("")) {
      WebUtil.redirect(response, "/pilot-project/");
      return;
    }

    // 유저정보 가져오기
    UserDao userDao = UserDao.INSTANCE;
    UserVo authUser = userDao.get(userVo);

    // 로그인성공시
    if (authUser != null) {
      // 인증 성공 (로그인처리)
      HttpSession session = request.getSession(true);
      session.setAttribute("authUser", authUser);
    }

    WebUtil.redirect(response, "/pilot-project/");
  }

}