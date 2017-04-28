package com.zum.pilot.filter;

import java.io.IOException;

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

import com.zum.pilot.action.BoardConstant;

@WebFilter(filterName = "BoardLoginFilter")
public class BoardLoginFilter implements Filter {

    public BoardLoginFilter() {
    }

    @Override
	public void destroy() {
	}

    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		String actionName = request.getParameter("action");

		if(BoardConstant.VIEW.equals(actionName) || actionName == null) {
			chain.doFilter(request, response);
		} else {
			HttpSession session = req.getSession();
			if(session.getAttribute("authUser") == null) {	// 로그인한 사용자가 아닐때
				res.sendRedirect(req.getContextPath() + "/board");
			} else {
				chain.doFilter(request, response);
			}
		}
	}

    @Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
