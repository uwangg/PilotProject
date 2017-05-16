package com.zum.pilot.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.pilot.action.Action;
import com.zum.pilot.dao.PostDao;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.PostVo;
import com.zum.pilot.vo.UserVo;

public enum PostModifyFormAction implements Action {
  INSTANCE;

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // 로그인하지 않은 사용자
    HttpSession session = request.getSession();

    if (request.getParameter("id") == null) {
      WebUtil.redirect(response, "/pilot-project/board");
      return;
    }

    Long id = Long.parseLong(request.getParameter("id"));
    UserVo authUser = (UserVo) session.getAttribute("authUser");

    PostDao postDao = PostDao.INSTANCE;
    PostVo vo = postDao.get(id);

    // id값이 범위를 벗어날때
    if (vo == null) {
      WebUtil.redirect(response, "/pilot-project/board");
      return;
    }
    // 작성자와 로그인한 유저가 다를때
    if (vo.getUserId() != authUser.getId()) {
      WebUtil.redirect(response, "/pilot-project/board");
      return;
    }
    request.setAttribute("vo", vo);
    WebUtil.forward(request, response, "/WEB-INF/views/board/modify.jsp");
  }

}
