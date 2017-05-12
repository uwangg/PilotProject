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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum PostWriteAction implements Action {
  INSTANCE;

  private static final Logger logger =
          LoggerFactory.getLogger(PostWriteAction.class);

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HttpSession session = request.getSession();
    UserVo authUser = (UserVo) session.getAttribute("authUser");

    // 업로드용 폴더 이름
    MultipartRequest multi = null;
    int maxSize = 5 * 1024 * 1024;    // 10M
    String title = "";
    String content = "";
    String imagePath = "";
    String fileName = "";

    // 파일이 업로드될 실제 tomcat 폴더의 경로
    String savePath = "D:\\test\\upload";
    logger.info("savePath = " + savePath);
    try {
      multi = new MultipartRequest(request, savePath, maxSize, "utf-8", new DefaultFileRenamePolicy());
      title = multi.getParameter("title");
      content = multi.getParameter("content");
      fileName = multi.getFilesystemName("imagePath");
      imagePath = fileName;
    } catch (Exception e) {
      e.printStackTrace();
    }

    // 게시글 입력
    PostVo postVo = new PostVo(title, content, imagePath, authUser.getId());
    PostDao postDao = PostDao.INSTANCE;
    postDao.insert(postVo);
    WebUtil.redirect(response, "/pilot-project/board");
  }

}
