package com.zum.pilot.controller;

import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;
import com.zum.pilot.action.BoardConstant;
import com.zum.pilot.action.board.BoardActionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
  @RequestMapping(value = "/" + BoardConstant.VIEW)
  public void view() {}

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
