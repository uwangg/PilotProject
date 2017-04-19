package com.zum.pilot.action.board;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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

@MultipartConfig
public class PostModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		System.out.println("postmodifyaction");

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
		String save_path = request.getServletContext().getRealPath("upload");
		boolean changed_image = false;
		
		try {
			multi = new MultipartRequest(request, save_path, maxSize, "utf-8", new DefaultFileRenamePolicy());
			id = Long.parseLong(multi.getParameter("id"));
			title = multi.getParameter("title");
			content = multi.getParameter("content");
			old_path = multi.getParameter("old_imgpath");
			image_path = multi.getFilesystemName("image_path");
			if(image_path == null || image_path.equals(""))
				image_path = old_path;
			else {
				changed_image = true;
			}
			user_id = Long.parseLong(multi.getParameter("user_id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		// 작성자만 수정 가능
		if(authUser.getId() == user_id) {
			PostVo vo = new PostVo(id, title, content, image_path, user_id);
			PostDao postDao = new PostDao(new MySQLConnection());
			
			// 이미지가 변경되었다면 이전 이미지는 서버에서 삭제
			if(changed_image) {
				PostVo postVo = postDao.get(id);
					
				String upload_file_name = request.getServletContext().getRealPath("upload");
				File upload_file = new File(upload_file_name + "/" + postVo.getImage_path());
					
				if(upload_file.exists() && upload_file.isFile())
					upload_file.delete();
			}
			postDao.update(vo);
		}
		
		WebUtil.redirect(request, response, "/pilot-project/board?a=view&id="+id);
	}

}
