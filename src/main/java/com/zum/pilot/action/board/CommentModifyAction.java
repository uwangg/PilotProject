package com.zum.pilot.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.pilot.action.Action;
import com.zum.pilot.dao.CommentDao;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.CommentVo;
import com.zum.pilot.vo.UserVo;

public enum CommentModifyAction implements Action {
  INSTANCE;

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HttpSession session = request.getSession();
    UserVo authUser = (UserVo) session.getAttribute("authUser");

    Long userId = authUser.getId();
    Long postId = Long.parseLong(request.getParameter("postId"));
    Long id = Long.parseLong(request.getParameter("id"));
    String content = request.getParameter("content");

    CommentDao commentDao = CommentDao.INSTANCE;

    CommentVo commentVo = new CommentVo();
    commentVo.setId(id);
    commentVo.setContent(content);
    commentVo.setUserId(userId);

    commentDao.update(commentVo);

    WebUtil.redirect(response, "/pilot-project/board?action=view&id=" + postId);
  }

}