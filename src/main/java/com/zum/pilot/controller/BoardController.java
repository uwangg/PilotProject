package com.zum.pilot.controller;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.zum.pilot.constant.BoardConstant;
import com.zum.pilot.service.CommentService;
import com.zum.pilot.service.PostService;
import com.zum.pilot.vo.CommentVo;
import com.zum.pilot.vo.PostVo;
import com.zum.pilot.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BoardController{

  @Autowired
  private CommentService commentService;

  @Autowired
  private PostService postService;

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
    postService.insert(postVo);
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
    /* service.readPost(postId) {
        Post post = dao.getPost(postId);
        post.read();
        Post.read {
          readCnt ++;
          if (readCnt > threshold) bestPost = true;
        }
        dao.update(post);
        return post;
        }
    */
    PostVo postVo = postService.readPost(postId);

    // 댓글 페이지네이션
    int totalPageNum = 0;    // 총 페이지 번호의 수
    int commentUnit = 10;    // 한 페이지당 보여줄 글의 최대 갯수
    int pageNumUnit = 5;    // 한 페이지 블락당 보여줄 번호의 최대 갯수
    int end = 1;

    // 게시물 id에 맞는 댓글 불러오기
    List<CommentVo> commentList = null;
    totalPageNum = commentService.totalNumberOfPage(postId, commentUnit);
    commentList = commentService.getList(postId, (totalPageNum - currentPageNum) * commentUnit, commentUnit);

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

    UserVo authUser = (UserVo) session.getAttribute("authUser");
    PostVo vo = postService.getPost(postId);

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

      // 이미지가 변경되었다면 이전 이미지는 서버에서 삭제
      if (changedImage) {
        PostVo postVo = postService.getPost(id);

        String uploadFileName = "D:\\test\\upload";
        File uploadFile = new File(uploadFileName + "/" + postVo.getImagePath());

        if (uploadFile.exists() && uploadFile.isFile())
          uploadFile.delete();
      }
      postService.update(vo);
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

    // 이미지가 있다면 삭제
    PostVo postVo = postService.getPost(postId);

    if (postVo.getUserId() == authId) {
      // 이미지가 있다면 삭제
      if (postVo.getImagePath() != null && !postVo.getImagePath().equals("")) {
        String uploadFileName = "D:\\test\\upload";
        logger.info("file path = " + uploadFileName);
        File uploadFile = new File(uploadFileName + "/" + postVo.getImagePath());

        if (uploadFile.exists() && uploadFile.isFile())
          uploadFile.delete();
      }
      // 게시글 삭제
      postService.delete(postId, authId);
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

    if (depth == 0) {    // 댓글을 다는 경우
      thread = commentService.getMaxThread(postId);
      thread = (thread / thrUnit) * thrUnit + thrUnit;
      commentVo.setThread(thread);
    } else {    // 답글을 다는 경우
      commentVo.setThread(thread);
      int preCommentThread = (thread / thrUnit) * thrUnit;
      commentService.updateThread(preCommentThread, thread + 1);
    }
    commentService.insert(commentVo);
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

    CommentVo commentVo = new CommentVo();
    commentVo.setId(commentId);
    commentVo.setContent(content);
    commentVo.setUserId(userId);
    commentService.update(commentVo);

    return "redirect:/board/{postId}";
  }

  // 댓글 삭제
  @RequestMapping(value = "/{postId}/" + BoardConstant.COMMENT_DELETE + "/{commentId}")
  public String commentDelete(@PathVariable Long postId,
                            @PathVariable Long commentId,
                            HttpSession session) {
    logger.info(BoardConstant.COMMENT_DELETE);

    UserVo authUser = (UserVo) session.getAttribute("authUser");

    Long userId = authUser.getId();
    commentService.delete(commentId, userId);

    return "redirect:/board/{postId}";
  }
}
