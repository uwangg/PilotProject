package com.zum.pilot.controller;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.zum.pilot.constant.BoardConstant;
import com.zum.pilot.entity.*;
import com.zum.pilot.service.CommentService;
import com.zum.pilot.service.PostService;
import com.zum.pilot.util.PageConstant;
import com.zum.pilot.util.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

  @Autowired
  private PostService postService;

  @Autowired
  private CommentService commentService;

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

    UserEntity authUser = (UserEntity) session.getAttribute("authUser");

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
    PostEntity postEntity = new PostEntity();
    postEntity.setTitle(title);
    postEntity.setContent(content);
    postEntity.setImagePath(imagePath);
    postEntity.setUserEntity(authUser);
    postService.create(postEntity);
    return "redirect:/board";
  }

  // 글 보기
  @RequestMapping(value = "/{postId}")
  public String view(
          @PathVariable Long postId,  // 게시글 id
          @RequestParam(value = "currentPage", defaultValue = "1") int currentPage, // 현재 눌린 페이지 번호
          Model model) {
    logger.info(BoardConstant.VIEW);
    // 게시글 id를 이용해 게시물 불러오기
      PostEntity postEntity = postService.readPost(postId);

      // 댓글 페이지네이션
    PageRequest pageRequest = new PageRequest(currentPage-1, PageConstant.ELEMENT_UNIT, Sort.Direction.DESC, "thread");
    Page<CommentEntity> page = commentService.findAllCommentList(postId, pageRequest);
    List<CommentEntity> comments = page.getContent();
    Long totalComments = page.getTotalElements();
    Pagination<CommentEntity> pagination = new Pagination<>(currentPage, totalComments, comments);

    model.addAttribute("postEntity", postEntity);
    model.addAttribute("pagination", pagination);

    return "board/view";
  }

  // 글 수정
  @RequestMapping(value = "/{postId}/" + BoardConstant.MODIFY, method = RequestMethod.GET)
  public String modifyForm(@PathVariable Long postId,
                           Model model,
                           HttpSession session) {
    logger.info(BoardConstant.MODIFY_FORM);

    UserEntity authUser = (UserEntity) session.getAttribute("authUser");
    PostEntity postEntity = postService.getPost(postId);

    // id값이 범위를 벗어날때
    if (postEntity == null) {
      return "redirect:/board";
    }
    // 작성자와 로그인한 유저가 다를때
    if (postEntity.getUserEntity().getId() != authUser.getId()) {
      return "redirect:/board";
    }
    model.addAttribute("postEntity", postEntity);
    return "board/" + BoardConstant.MODIFY;
  }

  @RequestMapping(value = "/{postId}/" + BoardConstant.MODIFY, method = RequestMethod.POST)
  public String modify(@PathVariable Long postId,
                     HttpServletRequest request) {
    logger.info(BoardConstant.MODIFY);

    // 업로드용 폴더 이름
    MultipartRequest multi = null;
    int maxSize = 5 * 1024 * 1024;    // 10M
//    Long id = -1L;
    String title = "";
    String content = "";
    String oldPath = "";
    String imagePath = "";
    Long userId = -1L;

    // 파일이 업로드될 실제 tomcat 폴더의 경로
    String savePath = "D:\\test\\upload";
    boolean changedImage = false;

    try {
      multi = new MultipartRequest(request, savePath, maxSize, "utf-8", new DefaultFileRenamePolicy());
      title = multi.getParameter("title");
      content = multi.getParameter("content");
      oldPath = multi.getParameter("oldImgPath");
      imagePath = multi.getFilesystemName("imagePath");
      if ("".equals(imagePath) || imagePath == null)
        imagePath = oldPath;
      else {
        changedImage = true;
      }
      userId = Long.parseLong(multi.getParameter("userId"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    HttpSession session = request.getSession();
    UserEntity authUser = (UserEntity) session.getAttribute("authUser");
    // 작성자만 수정 가능
    if (authUser.getId() == userId) {
      // 이미지가 변경되었다면 이전 이미지는 서버에서 삭제
      if (changedImage) {
        PostEntity prePost = postService.getPost(postId);
        String uploadFileName = "D:\\test\\upload";
        File uploadFile = new File(uploadFileName + "/" + prePost.getImagePath());
        if (uploadFile.exists() && uploadFile.isFile())
          uploadFile.delete();
      }
      postService.modifyPost(postId, title, content, imagePath);
    }
    return "redirect:/board/{postId}";
  }

  // 글 삭제
  @RequestMapping(value = "/{postId}/" + BoardConstant.DELETE)
  public String delete(
          @PathVariable Long postId,
          HttpSession session) {
    logger.info(BoardConstant.DELETE);

    UserEntity authUser = (UserEntity) session.getAttribute("authUser");
    Long authId = authUser.getId();    // 글을 확인하는 사람

    // 이미지가 있다면 삭제
    PostEntity postEntity = postService.getPost(postId);

    if (postEntity.getUserId() == authId) {
      // 이미지가 있다면 삭제
      if (postEntity.getImagePath() != null && !postEntity.getImagePath().equals("")) {
        String uploadFileName = "D:\\test\\upload";
        logger.info("file path = " + uploadFileName);
        File uploadFile = new File(uploadFileName + "/" + postEntity.getImagePath());

        if (uploadFile.exists() && uploadFile.isFile())
          uploadFile.delete();
      }
      // 게시글 삭제
      postService.deletePost(postId);
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

    // 댓글 작성자 정보 가져오기
    UserEntity authUser = (UserEntity) session.getAttribute("authUser");
    Long userId = authUser.getId();

    CommentEntity commentEntity = new CommentEntity(content, depth, postId, userId);

    commentService.writeComment(commentEntity, depth, thread);
    return "redirect:/board/{postId}";
  }

  // 댓글 수정
  @RequestMapping(value = "/{postId}/" + BoardConstant.COMMENT_MODIFY + "/{commentId}")
  public String commentModify(@PathVariable Long postId,
                            @PathVariable Long commentId,
                            @RequestParam("content") String content,
                            HttpSession session) {
    UserEntity authUser = (UserEntity) session.getAttribute("authUser");
    Long userId = authUser.getId();


    CommentEntity commentEntity = commentService.getComment(commentId);
    if(commentEntity.getUserId() == userId) {
      commentEntity.setContent(content);
      commentService.modifyComment(commentEntity);
    }
    return "redirect:/board/{postId}";
  }

  // 댓글 삭제
  @RequestMapping(value = "/{postId}/" + BoardConstant.COMMENT_DELETE + "/{commentId}")
  public String commentDelete(@PathVariable Long postId,
                            @PathVariable Long commentId,
                            HttpSession session) {
    logger.info(BoardConstant.COMMENT_DELETE);

    UserEntity authUser = (UserEntity) session.getAttribute("authUser");

    Long userId = authUser.getId();
    CommentEntity commentEntity = commentService.getComment(commentId);
    if(commentEntity.getUserId() == userId) {
      commentService.deleteComment(commentId);
    }

    return "redirect:/board/{postId}";
  }
}
