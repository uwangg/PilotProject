package com.zum.pilot.controller;

import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;
import com.zum.pilot.action.BoardConstant;
import com.zum.pilot.action.board.BoardActionFactory;
import com.zum.pilot.dao.CommentDao;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.CommentVo;
import com.zum.pilot.vo.PostVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController{

  private static final Logger logger =
          LoggerFactory.getLogger(BoardController.class);

  @RequestMapping("")
  public String main() {
    return "redirect:/";
  }

  // 글 쓰기
  @RequestMapping(value = "/" + BoardConstant.WRITE, method = RequestMethod.GET)
  public void writeForm() {
  }

  @RequestMapping(value = "/" + BoardConstant.WRITE, method = RequestMethod.POST)
  public void write() {

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
  @RequestMapping(value = "/" + BoardConstant.MODIFY, method = RequestMethod.GET)
  public void modifyForm() {}

  @RequestMapping(value = "/" + BoardConstant.MODIFY, method = RequestMethod.POST)
  public void modify() {}

  // 글 삭제
  @RequestMapping(value = "/" + BoardConstant.DELETE)
  public void delete() {}

  // 댓글 쓰기
  @RequestMapping(value = "/" + BoardConstant.COMMENT_WRITE)
  public void commentWrite() {}

  // 댓글 수정
  @RequestMapping(value = "/" + BoardConstant.COMMENT_MODIFY)
  public void commentModify() {}

  // 댓글 삭제
  @RequestMapping(value = "/" + BoardConstant.COMMENT_DELETE)
  public void commentDelete() {}
}
