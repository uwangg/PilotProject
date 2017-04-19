package com.zum.pilot.action.board;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.db.MySQLConnection;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.PostVo;
import com.zum.pilot.vo.UserVo;

public class PostDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		Long authId = authUser.getId();	// 글을 확인하는 사람
	
		Long id = Long.parseLong(request.getParameter("id"));
		Long userId = Long.parseLong(request.getParameter("user_id"));	// 게시글 작성자
		
		if(userId == authId) {		
			PostDao postDao = new PostDao(new MySQLConnection());
			
			// 이미지가 있다면 삭제
			PostVo postVo = postDao.get(id);
			if(postVo.getImagePath() != null && !postVo.getImagePath().equals("")) {
				
				String uploadFileName = request.getServletContext().getRealPath("upload");
				File uploadFile = new File(uploadFileName + "/" + postVo.getImagePath());
				
				if(uploadFile.exists() && uploadFile.isFile())
					uploadFile.delete();
			}
			
			// 게시글 삭제
			postDao.delete(id, authUser.getId());
		}
		WebUtil.redirect(request, response, "/pilot-project/board");
	}

}
