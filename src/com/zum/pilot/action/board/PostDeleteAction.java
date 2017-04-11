package com.zum.pilot.action.board;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.db.MySQLConnection;
import com.zum.pilot.WebUtil;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.vo.PostVo;
import com.zum.pilot.vo.UserVo;

public class PostDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		if(session == null) {
			WebUtil.redirect(request, response, "/pilot-project/board");
			return;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		Long auth_id = authUser.getId();	// 글을 확인하는 사람
	
		Long id = Long.parseLong(request.getParameter("id"));
		Long user_id = Long.parseLong(request.getParameter("user_id"));	// 게시글 작성자
		
		if(user_id == auth_id) {		
			PostDao postDao = new PostDao(new MySQLConnection());
			
			// 이미지가 있다면 삭제
			PostVo postVo = postDao.get(id);
			if(!postVo.getImage_path().equals("")) {
				
				String upload_file_name = request.getServletContext().getRealPath("upload");
				File upload_file = new File(upload_file_name + "/" + postVo.getImage_path());
				
				if(upload_file.exists() && upload_file.isFile())
					upload_file.delete();
			}
			
			// 게시글 삭제
			postDao.delete(id);
		}
		WebUtil.redirect(request, response, "/pilot-project/board");
	}

}
