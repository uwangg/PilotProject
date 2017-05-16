package com.zum.pilot.interceptor;

import com.zum.pilot.dao.UserDao;
import com.zum.pilot.util.SecurityUtil;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
  private static final Logger logger =
          LoggerFactory.getLogger(AuthLoginInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    logger.info("AuthLoginInterceptor.........");

    String email = request.getParameter("email");
    String password = SecurityUtil.encryptSHA256(request.getParameter("password"));

    UserVo userVo = new UserVo();
    userVo.setEmail(email);
    userVo.setPassword(password);

    // 유저정보 가져오기
    UserDao userDao = UserDao.INSTANCE;
    UserVo authUser = userDao.get(userVo);

    // 로그인성공시
    if (authUser != null) {
      // 인증 성공 (로그인처리)
      HttpSession session = request.getSession(true);
      session.setAttribute("authUser", authUser);
    }

    response.sendRedirect(request.getContextPath() + "/");
    return false;
  }
}
