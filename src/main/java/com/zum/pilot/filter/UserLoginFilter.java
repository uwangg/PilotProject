package com.zum.pilot.filter;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.pilot.constant.UserConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(filterName = "UserLoginFilter")
public class UserLoginFilter implements Filter {

  private static final Logger logger =
          LoggerFactory.getLogger(UserLoginFilter.class);

  public UserLoginFilter() {
  }

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    logger.info("UserLoginFilter");

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    String[] filterParam = {UserConstant.LOGOUT, UserConstant.MODIFY_FORM, UserConstant.MODIFY, UserConstant.WITHDRAWAL_FORM, UserConstant.WITHDRAWAL};
    HashSet<String> params = new HashSet<String>();
    for (int i = 0; i < filterParam.length; i++) {
      params.add(filterParam[i]);
    }
    String actionName = request.getParameter("action");
    if (params.contains(actionName)) {
      HttpSession session = req.getSession();
      if (session.getAttribute("authUser") == null) {    // 로그인한 사용자가 아닐때
        logger.info("로그인 한 사용자가 아닙니다");
        res.sendRedirect(req.getContextPath() + "/user");
      } else {
        logger.info("로그인 한 사용자입니다");
        chain.doFilter(request, response);
      }
    } else {
      chain.doFilter(request, response);
    }
  }

  @Override
  public void init(FilterConfig fConfig) throws ServletException {
  }

}
