package com.zum.pilot.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
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
		String imagePath = "";
		String fileName = "";
		
		// 파일이 업로드될 실제 tomcat 폴더의 경로
		String savePath = request.getServletContext().getRealPath("upload");
		
		try {
			multi = new MultipartRequest(request, savePath, maxSize, "utf-8", new DefaultFileRenamePolicy());
			title = multi.getParameter("title");
			content = multi.getParameter("content");
			fileName = multi.getFilesystemName("image_path");
			imagePath = fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("title = " + title);
		System.out.println("content = " + content);
		System.out.println("imagePath = " + imagePath);
		
		// 게시글 입력
		PostVo postVo = new PostVo(title, content, imagePath, authUser.getId());
//		PostDao postDao = new PostDao(new MySQLConnection());
		PostDao postDao = new PostDao();
		postDao.insert(postVo);
		WebUtil.redirect(request, response, "/pilot-project/board");
	}

}
