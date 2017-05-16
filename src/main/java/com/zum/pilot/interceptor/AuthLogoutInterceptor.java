package com.zum.pilot.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthLogoutInterceptor extends HandlerInterceptorAdapter {
  private static final Logger logger =
          LoggerFactory.getLogger(AuthLogoutInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    logger.info("AuthLogoutInterceptor...........");
    HttpSession session = request.getSession();

    //로그아웃 처리
    session.removeAttribute("authUser");    // 세션 삭제
    session.invalidate();    // 세션 종료
    response.sendRedirect(request.getContextPath() + "/");
    return false;
  }
}
