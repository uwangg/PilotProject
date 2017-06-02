package com.zum.pilot.interceptor;

import com.zum.pilot.util.ScriptUtil;
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
      String msg = "로그인한 사용자가 아닙니다. 로그인 해주세요. \\nHome으로 돌아갑니다";
      String url = "/";
      ScriptUtil.alert(response, msg, url);
      return false;
    }
    return true;
  }
}
