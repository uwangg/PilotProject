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
import com.zum.pilot.action.Action;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.PostVo;
import com.zum.pilot.vo.UserVo;

@MultipartConfig
public enum PostModifyAction implements Action {
	INSTANCE;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
		System.out.println("postmodifyaction");

		// 업로드용 폴더 이름
		MultipartRequest multi = null;
		int maxSize = 5*1024*1024;	// 10M
		Long id = -1L;
		String title = "";
		String content = "";
		String oldPath = "";
		String imagePath = "";
		Long userId = -1L;
		
		// 파일이 업로드될 실제 tomcat 폴더의 경로
//		String savePath = request.getServletContext().getRealPath("upload");
		String savePath = "D:\\test\\upload";
		boolean changedImage = false;
		
		try {
			multi = new MultipartRequest(request, savePath, maxSize, "utf-8", new DefaultFileRenamePolicy());
			id = Long.parseLong(multi.getParameter("id"));
			title = multi.getParameter("title");
			content = multi.getParameter("content");
			oldPath = multi.getParameter("old_imgpath");
			imagePath = multi.getFilesystemName("image_path");
			if(imagePath == null || imagePath.equals(""))
				imagePath = oldPath;
			else {
				changedImage = true;
			}
			userId = Long.parseLong(multi.getParameter("user_id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		// 작성자만 수정 가능
		if(authUser.getId() == userId) {
			PostVo vo = new PostVo(id, title, content, imagePath, userId);
//			PostDao postDao = new PostDao(new MySQLConnection());
//			PostDao postDao = new PostDao();
			PostDao postDao = PostDao.INSTANCE;
			
			// 이미지가 변경되었다면 이전 이미지는 서버에서 삭제
			if(changedImage) {
				PostVo postVo = postDao.get(id);
					
//				String uploadFileName = request.getServletContext().getRealPath("upload");
				String uploadFileName = "D:\\test\\upload";
				File uploadFile = new File(uploadFileName + "/" + postVo.getImagePath());
					
				if(uploadFile.exists() && uploadFile.isFile())
					uploadFile.delete();
			}
			postDao.update(vo);
		}
		
		WebUtil.redirect(request, response, "/pilot-project/board?a=view&id="+id);
	}

}
