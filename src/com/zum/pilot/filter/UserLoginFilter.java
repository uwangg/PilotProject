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

import com.zum.pilot.action.UserConstant;

@WebFilter(filterName = "UserLoginFilter")
public class UserLoginFilter implements Filter {

    public UserLoginFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		request.setCharacterEncoding("utf-8");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		String[] filterParam = {UserConstant.LOGOUT, UserConstant.MODIFY_FORM, UserConstant.MODIFY, UserConstant.WITHDRAWAL_FORM, UserConstant.WITHDRWAL};
		HashSet<String> params = new HashSet<String>();
		for(int i=0 ; i<filterParam.length ; i++) {
			params.add(filterParam[i]);
		}
		String actionName = request.getParameter("a");
		System.out.println("actionName = " + actionName);
		if(params.contains(actionName)) {
			HttpSession session = req.getSession();
			if(session.getAttribute("authUser") == null) {	// 로그인한 사용자가 아닐때
				res.sendRedirect(req.getContextPath() + "/user");
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
