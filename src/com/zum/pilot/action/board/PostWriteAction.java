package com.zum.pilot.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.zum.db.MySQLConnection;
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.PostVo;
import com.zum.pilot.vo.UserVo;

public class PostWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		// 업로드용 폴더 이름
		MultipartRequest multi = null;
		int maxSize = 5*1024*1024;	// 10M
		String title = "";
		String content = "";
		String image_path = "";
		String file_name = "";
		
		// 파일이 업로드될 실제 tomcat 폴더의 경로
		String save_path = request.getServletContext().getRealPath("upload");
		
		try {
			multi = new MultipartRequest(request, save_path, maxSize, "utf-8", new DefaultFileRenamePolicy());
			title = multi.getParameter("title");
			content = multi.getParameter("content");
			file_name = multi.getFilesystemName("image_path");
			image_path = file_name;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("title = " + title);
		System.out.println("content = " + content);
		System.out.println("image_path = " + image_path);
		
		// 게시글 입력
		PostVo postVo = new PostVo(title, content, image_path, authUser.getId());
		PostDao postDao = new PostDao(new MySQLConnection());
		postDao.insert(postVo);
		WebUtil.redirect(request, response, "/pilot-project/board");
	}

}
