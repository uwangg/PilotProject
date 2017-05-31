package com.zum.pilot.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor extends HandlerInterceptorAdapter {
  private final static Logger logger =
          LoggerFactory.getLogger(AuthInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    logger.info("AuthInterceptor...................");
    HttpSession session = request.getSession();
    if(session.getAttribute("authUser") == null) {
      logger.info("로그인한 사용자가 아닙니다.");
      response.sendRedirect(request.getContextPath() + "/");
      return false;
    }
    return true;
  }
}
