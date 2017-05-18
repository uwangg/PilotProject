package com.zum.pilot.controller;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sun.javafx.sg.prism.NGShape;
import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;
import com.zum.pilot.action.BoardConstant;
import com.zum.pilot.action.board.BoardActionFactory;
import com.zum.pilot.dao.CommentDao;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.CommentVo;
import com.zum.pilot.vo.PostVo;
import com.zum.pilot.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController{

  @Autowired
  private  ServletContext context;

  private static final Logger logger =
          LoggerFactory.getLogger(BoardController.class);

  @RequestMapping("")
  public String main() {
    return "redirect:/";
  }

  // 글 쓰기
  @RequestMapping(value = "/" + BoardConstant.WRITE, method = RequestMethod.GET)
  public void writeForm() {
    logger.info(BoardConstant.WRITE_FORM);
  }

  @RequestMapping(value = "/" + BoardConstant.WRITE, method = RequestMethod.POST)
  public String write(
                    HttpSession session,
                    HttpServletRequest request) {
    logger.info(BoardConstant.WRITE);

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
//    String savePath = context.getRealPath("upload");
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
    return "redirect:/board";
  }

  // 글 보기
  @RequestMapping(value = "/{postId}")
  public String view(
          @PathVariable Long postId,  // 게시글 id
          @RequestParam(value = "currentPageNum", defaultValue = "1") int currentPageNum, // 현재 눌린 페이지 번호
          @RequestParam(value = "begin", defaultValue = "1") int begin, // 시작 페이지
          Model model) {
    logger.info(BoardConstant.VIEW);
    // 게시글 id를 이용해 게시물 불러오기
    PostDao postDao = PostDao.INSTANCE;
    postDao.hitIncrease(postId);
    PostVo postVo = postDao.get(postId);

    // 댓글 페이지네이션
    Long totalCommentNum = 0L;    // 게시글의 총 갯수
    int totalPageNum = 0;    // 총 페이지 번호의 수
    int commentUnit = 10;    // 한 페이지당 보여줄 글의 최대 갯수
    int pageNumUnit = 5;    // 한 페이지 블락당 보여줄 번호의 최대 갯수
    int end = 1;

    // 게시물 id에 맞는 댓글 불러오기
    List<CommentVo> commentList = null;
    CommentDao commentDao = CommentDao.INSTANCE;

    totalCommentNum = commentDao.totalNumberOfComment(postId);
    totalPageNum = (int) ((totalCommentNum - 1) / commentUnit + 1);
    logger.info("댓글 수 : " + totalCommentNum + ", 페이지 번호 수 : " + totalPageNum);

    commentList = commentDao.getList(postId, (totalPageNum - currentPageNum) * commentUnit, commentUnit);

    // 끝페이지 가져오기
    end = (begin - 1) + pageNumUnit;
    if (end > totalPageNum)
      end = totalPageNum;

    model.addAttribute("postVo", postVo);
    model.addAttribute("commentList", commentList);
    model.addAttribute("begin", begin);
    model.addAttribute("end", end);
    model.addAttribute("totalPageNum", totalPageNum);
    model.addAttribute("currentPageNum", currentPageNum);
    model.addAttribute("pageNumUnit", pageNumUnit);

    return "board/view";
  }

  // 글 수정
  @RequestMapping(value = "/{postId}/" + BoardConstant.MODIFY, method = RequestMethod.GET)
  public String modifyForm(@PathVariable Long postId,
                           Model model,
                           HttpSession session) {
    logger.info(BoardConstant.MODIFY_FORM);

    // 로그인하지 않은 사용자
//    if (postId == null) {
//      WebUtil.redirect(response, "/pilot-project/board");
//      return;
//    }

    UserVo authUser = (UserVo) session.getAttribute("authUser");

    PostDao postDao = PostDao.INSTANCE;
    PostVo vo = postDao.get(postId);

    // id값이 범위를 벗어날때
    if (vo == null) {
      return "redirect:/board";
    }
    // 작성자와 로그인한 유저가 다를때
    if (vo.getUserId() != authUser.getId()) {
      return "redirect:/board";
    }
    model.addAttribute("vo", vo);
    return "board/" + BoardConstant.MODIFY;
  }

  @RequestMapping(value = "/{postId}/" + BoardConstant.MODIFY, method = RequestMethod.POST)
  public String modify(@PathVariable Long postId,
                     HttpServletRequest request) {
    logger.info(BoardConstant.MODIFY);

    // 업로드용 폴더 이름
    MultipartRequest multi = null;
    int maxSize = 5 * 1024 * 1024;    // 10M
    Long id = -1L;
    String title = "";
    String content = "";
    String oldPath = "";
    String imagePath = "";
    Long userId = -1L;

    // 파일이 업로드될 실제 tomcat 폴더의 경로
    String savePath = "D:\\test\\upload";
//    String savePath = context.getRealPath("upload");
    boolean changedImage = false;

    try {
      multi = new MultipartRequest(request, savePath, maxSize, "utf-8", new DefaultFileRenamePolicy());
      id = Long.parseLong(multi.getParameter("id"));
      title = multi.getParameter("title");
      content = multi.getParameter("content");
      oldPath = multi.getParameter("oldimgpath");
      imagePath = multi.getFilesystemName("imagePath");
      if (imagePath == null || imagePath.equals(""))
        imagePath = oldPath;
      else {
        changedImage = true;
      }
      userId = Long.parseLong(multi.getParameter("userId"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    HttpSession session = request.getSession();
    UserVo authUser = (UserVo) session.getAttribute("authUser");

    // 작성자만 수정 가능
    if (authUser.getId() == userId) {
      PostVo vo = new PostVo(id, title, content, imagePath, userId);
      PostDao postDao = PostDao.INSTANCE;

      // 이미지가 변경되었다면 이전 이미지는 서버에서 삭제
      if (changedImage) {
        PostVo postVo = postDao.get(id);

        String uploadFileName = "D:\\test\\upload";
        File uploadFile = new File(uploadFileName + "/" + postVo.getImagePath());

        if (uploadFile.exists() && uploadFile.isFile())
          uploadFile.delete();
      }
      postDao.update(vo);
    }

    return "redirect:/board/{postId}";
  }

  // 글 삭제
  @RequestMapping(value = "/{postId}/" + BoardConstant.DELETE)
  public String delete(
          @PathVariable Long postId,
          HttpSession session) {
    logger.info(BoardConstant.DELETE);

    UserVo authUser = (UserVo) session.getAttribute("authUser");
    Long authId = authUser.getId();    // 글을 확인하는 사람

    PostDao postDao = PostDao.INSTANCE;
    // 이미지가 있다면 삭제
    PostVo postVo = postDao.get(postId);

    if (postVo.getUserId() == authId) {
      // 이미지가 있다면 삭제
      if (postVo.getImagePath() != null && !postVo.getImagePath().equals("")) {

//        String uploadFileName = request.getServletContext().getRealPath("upload");
//        String uploadFileName = context.getRealPath("upload");
        String uploadFileName = "D:\\test\\upload";
        logger.info("file path = " + uploadFileName);
        File uploadFile = new File(uploadFileName + "/" + postVo.getImagePath());

        if (uploadFile.exists() && uploadFile.isFile())
          uploadFile.delete();
      }

      // 게시글 삭제
      try {
        postDao.delete(postId, authUser.getId());
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return "redirect:/board";
  }

  // 댓글 쓰기
  @RequestMapping(value = "/{postId}/" + BoardConstant.COMMENT_WRITE)
  public String commentWrite(@PathVariable Long postId,
                             @RequestParam("depth") int depth,
                             @RequestParam(value = "thread", defaultValue = "0") int thread,
                             @RequestParam("content") String content,
                             HttpSession session) {
    logger.info(BoardConstant.COMMENT_WRITE);
    final int thrUnit = 1000;

    logger.info("댓글 내용 : " + content);
    // 댓글 작성자 정보 가져오기
    UserVo authUser = (UserVo) session.getAttribute("authUser");
    Long userId = authUser.getId();

    CommentVo commentVo = new CommentVo();
    commentVo.setPostId(postId);
    commentVo.setUserId(userId);
    commentVo.setContent(content);
    commentVo.setDepth(depth);

    CommentDao commentDao = CommentDao.INSTANCE;

    if (depth == 0) {    // 댓글을 다는 경우
      thread = commentDao.getMaxThread(postId);
      thread = (thread / thrUnit) * thrUnit + thrUnit;
      commentVo.setThread(thread);
    } else {    // 답글을 다는 경우
      commentVo.setThread(thread);
      int precommentThread = (thread / thrUnit) * thrUnit;
      commentDao.updateThread(precommentThread, thread + 1);
    }

    commentDao.insert(commentVo);
//    WebUtil.redirect(response, "/pilot-project/board?action=view&id=" + postId);
    return "redirect:/board/{postId}";
  }

  // 댓글 수정
  @RequestMapping(value = "/{postId}/" + BoardConstant.COMMENT_MODIFY + "/{commentId}")
  public String commentModify(@PathVariable Long postId,
                            @PathVariable Long commentId,
                            @RequestParam("content") String content,
                            HttpSession session) {
    UserVo authUser = (UserVo) session.getAttribute("authUser");

    Long userId = authUser.getId();

    CommentDao commentDao = CommentDao.INSTANCE;

    CommentVo commentVo = new CommentVo();
    commentVo.setId(commentId);
    commentVo.setContent(content);
    commentVo.setUserId(userId);

    commentDao.update(commentVo);

//    WebUtil.redirect(response, "/pilot-project/board?action=view&id=" + postId);
    return "redirect:/board/{postId}";
  }

  // 댓글 삭제
  @RequestMapping(value = "/{postId}/" + BoardConstant.COMMENT_DELETE)
  public void commentDelete() {}
}
