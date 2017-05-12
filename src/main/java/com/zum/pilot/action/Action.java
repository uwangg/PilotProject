package com.zum.pilot.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 요청 파라미터로 명령어를 전달하는 방식의 슈퍼 인터페이스
public interface Action {
	public void execute(
		HttpServletRequest request,
		HttpServletResponse response)
	throws ServletException, IOException;	
}
