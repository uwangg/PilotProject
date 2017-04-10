package com.zum.pilot.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.zum.db.MySQLConnection;
import com.zum.pilot.WebUtil;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.vo.PostVo;
import com.zum.pilot.vo.UserVo;

@MultipartConfig
public class PostModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		System.out.println("postmodifyaction");
		HttpSession session = request.getSession();
		if(session == null) {
			WebUtil.redirect(request, response, "/pilot-project/board");
			return;
		}
		
		// 업로드용 폴더 이름
		MultipartRequest multi = null;
		int maxSize = 5*1024*1024;	// 10M
		Long id = -1L;
		String title = "";
		String content = "";
		String old_path = "";
		String image_path = "";
		Long user_id = -1L;
		
		// 파일이 업로드될 실제 tomcat 폴더의 경로
//		String save_path = request.getSession().getServletContext().getRealPath("upload");
//		String save_path = "D:\\git\\PilotProject\\WebContent\\upload";
		String save_path = request.getServletContext().getRealPath("upload");
		
		try {
			multi = new MultipartRequest(request, save_path, maxSize, "utf-8", new DefaultFileRenamePolicy());
			id = Long.parseLong(multi.getParameter("id"));
			title = multi.getParameter("title");
			content = multi.getParameter("content");
			old_path = multi.getFilesystemName("old_path");
			image_path = multi.getFilesystemName("image_path");
			if(image_path.equals(""))
				image_path = old_path;
			user_id = Long.parseLong(multi.getParameter("user_id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("수정");
		System.out.println("image_path = " + image_path);
//		Long id = Long.parseLong(request.getParameter("id"));
//		String title = request.getParameter("title");
//		String content = request.getParameter("content");
//		String image_path = request.getParameter("image_path");
//		Long user_id = Long.parseLong(request.getParameter("user_id"));
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser.getId() == user_id) {
			PostVo vo = new PostVo();
			vo.setId(id);
			vo.setTitle(title);
			vo.setContent(content);
			vo.setImage_path(image_path);
			vo.setUser_id(user_id);
		
			PostDao postDao = new PostDao(new MySQLConnection());
			postDao.update(vo);
		}
		
		WebUtil.redirect(request, response, "/pilot-project/board?a=view?id="+user_id);
	}

}
