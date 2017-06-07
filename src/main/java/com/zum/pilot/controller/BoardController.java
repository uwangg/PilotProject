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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

  @Resource(name = "uploadPath")
  String uploadPath;

  private static final Logger logger =
          LoggerFactory.getLogger(BoardController.class);

  @RequestMapping("")
  public String main() {
    return "redirect:/";
  }

  // 글 쓰기
  @RequestMapping(value = "/write", method = RequestMethod.GET)
  public void writeForm() {
    logger.debug(BoardConstant.WRITE_FORM);
  }

  @RequestMapping(value = "/write", method = RequestMethod.POST)
  public String write(
          HttpSession session,
          MultipartFile file,
          @ModelAttribute PostEntity postEntity) {
    logger.debug(BoardConstant.WRITE);

    UserEntity authUser = (UserEntity) session.getAttribute("authUser");
    Long userId = authUser.getId();
    postEntity.getUserEntity().setId(userId);

    if(!file.isEmpty()) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
      Date date = new Date();
      // 이미지 등록
      String saveName = sdf.format(date) + "_" + file.getOriginalFilename();
      File target = new File(uploadPath, saveName);
      // 임시 디렉토리에 저장된 업로드된 파일을 지정된 디렉토리로 복사
      try {
        FileCopyUtils.copy(file.getBytes(), target);
      } catch (IOException e) {
        e.printStackTrace();
      }
      postEntity.setImagePath(saveName);
    }
    // 게시글 입력
    postService.create(postEntity);
    return "redirect:/board";
  }

  // 글 보기
  @RequestMapping(value = "/{postId}")
  public String view(
          @PathVariable Long postId,  // 게시글 id
          @RequestParam(value = "currentPage", defaultValue = "1") int currentPage, // 현재 눌린 페이지 번호
          Model model) {
    logger.debug(BoardConstant.VIEW);
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
  @RequestMapping(value = "/{postId}/modify", method = RequestMethod.GET)
  public String modifyForm(@PathVariable Long postId,
                           Model model,
                           HttpSession session) {
    logger.debug(BoardConstant.MODIFY_FORM);

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
    return "board/modify";
  }
  @RequestMapping(value = "/{postId}/modify", method = RequestMethod.POST)
  public String modify(@PathVariable Long postId,
                       MultipartFile file,
                       @RequestParam String title,
                       @RequestParam String content,
                       HttpSession session) {
    logger.debug(BoardConstant.MODIFY);

    PostEntity postEntity = postService.getPost(postId);
    postEntity.setTitle(title);
    postEntity.setContent(content);
    String oldImgPath = postEntity.getImagePath();

    if(!file.isEmpty()) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
      Date date = new Date();
      // 이미지 등록
      String saveName = sdf.format(date) + "_" + file.getOriginalFilename();
      File target = new File(uploadPath, saveName);

      // 임시 디렉토리에 저장된 업로드된 파일을 지정된 디렉토리로 복사
      try {
        FileCopyUtils.copy(file.getBytes(), target);
      } catch (IOException e) {
        e.printStackTrace();
      }
      postEntity.setImagePath(saveName);
      if(oldImgPath != null) {
        // 원래 이미지 삭제
        File uploadFile = new File(uploadPath + "/" + oldImgPath);
        if (uploadFile.exists() && uploadFile.isFile())
          uploadFile.delete();
      }
    }
    postService.modifyPost(postEntity);
    return "redirect:/board/{postId}";
  }

  // 글 삭제
  @RequestMapping(value = "/{postId}/delete")
  public String delete(
          @PathVariable Long postId,
          HttpSession session) {
    logger.debug(BoardConstant.DELETE);

    UserEntity authUser = (UserEntity) session.getAttribute("authUser");
    Long authId = authUser.getId();    // 글을 확인하는 사람

    // 이미지가 있다면 삭제
    PostEntity postEntity = postService.getPost(postId);

    if (postEntity.getUserId() == authId) {
      // 이미지가 있다면 삭제
      if (postEntity.getImagePath() != null && !postEntity.getImagePath().equals("")) {
        String uploadFileName = "D:\\test\\upload";
        logger.debug("file path = " + uploadFileName);
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
  @RequestMapping(value = "/{postId}/writecomment")
  public String writeComment(@PathVariable Long postId,
                             @RequestParam("depth") int depth,
                             @RequestParam(value = "thread", defaultValue = "0") int thread,
                             @RequestParam("content") String content,
                             HttpSession session) {
    logger.debug(BoardConstant.WRITE_COMMENT);

    // 댓글 작성자 정보 가져오기
    UserEntity authUser = (UserEntity) session.getAttribute("authUser");
    Long userId = authUser.getId();

    CommentEntity commentEntity = new CommentEntity(content, depth, postId, userId);
    commentService.writeComment(commentEntity, depth, thread);
    return "redirect:/board/{postId}";
  }

  // 댓글 수정
  @RequestMapping(value = "/{postId}/modifycomment/{commentId}")
  public String modifyComment(@PathVariable Long postId,
                              @PathVariable Long commentId,
                              @RequestParam("content") String content,
                              HttpSession session) {
    UserEntity authUser = (UserEntity) session.getAttribute("authUser");
    Long userId = authUser.getId();
    commentService.modifyComment(commentId, userId, content);
    return "redirect:/board/{postId}";
  }

  // 댓글 삭제
  @RequestMapping(value = "/{postId}/deletecomment/{commentId}")
  public String deleteComment(@PathVariable Long postId,
                              @PathVariable Long commentId,
                              HttpSession session) {
    logger.debug(BoardConstant.DELETE_COMMENT);

    UserEntity authUser = (UserEntity) session.getAttribute("authUser");
    Long userId = authUser.getId();
    commentService.deleteComment(commentId, userId);
    return "redirect:/board/{postId}";
  }
}
