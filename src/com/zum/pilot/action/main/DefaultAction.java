package com.zum.pilot.action.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.db.MySQLConnection;
import com.zum.pilot.WebUtil;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.vo.PostVo;

public class DefaultAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 게시글 페이지네이션
		int totalPostNum = 0;	// 게시글의 총 갯수
		int totalPageNum = 0; 	// 총 페이지 번호의 수
		int postUnit = 10;	// 한 페이지당 보여줄 글의 최대 갯수
		int pageNumUnit = 5;	// 한 페이지 블락당 보여줄 번호의 최대 갯수
		int currentPageNum = 1;	// 눌린 페이지 번호
		int currentPage = 1;	// 현재 페이지 블락
		int begin = 1, end = 1;
		
		List<PostVo> postList = null;
		
		PostDao postDao = new PostDao(new MySQLConnection());
		
		totalPostNum = postDao.totalNumberOfPost();
		totalPageNum = (totalPostNum - 1) / postUnit + 1;
		
		// 현재 눌린 페이지 가져오기
		if(request.getParameter("currentPageNum") == null) {
			currentPageNum = 1;
		} else {
			currentPageNum = Integer.parseInt(request.getParameter("currentPageNum"));
		}
		postList = postDao.getList((totalPageNum-currentPageNum)*postUnit, postUnit);	// 선택된 페이지번호, 보여줄 글의갯수
		
		
		// 시작과 끝페이지 가져오기
		if(request.getParameter("begin") == null) {
			begin = 1;
		} else {
			begin = Integer.parseInt(request.getParameter("begin"));
		}
		end = (begin-1) + pageNumUnit;
		if(end > totalPageNum)
			end = totalPageNum;
		
		System.out.println("begin = "+begin+", end = "+end);
		
		request.setAttribute("begin", begin);
		request.setAttribute("end", end);
		request.setAttribute("totalPageNum", totalPageNum);
		request.setAttribute("currentPageNum", currentPageNum);
		request.setAttribute("pageNumUnit", pageNumUnit);
		request.setAttribute("postList", postList);
		
		WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
	}

}
