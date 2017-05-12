package com.zum.pilot.action.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zum.pilot.action.Action;
import com.zum.pilot.dao.UserDao;
import com.zum.pilot.util.SecurityUtil;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ModifyAction implements Action {
  INSTANCE;

  private static final Logger logger =
          LoggerFactory.getLogger(ModifyAction.class);

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HttpSession session = request.getSession();
    UserVo authUser = (UserVo) session.getAttribute("authUser");

    String name = request.getParameter("name");
    String password = request.getParameter("passwd");
    String changePassword = request.getParameter("changePasswd");
    String changeConfirm = request.getParameter("changeConfirm");

    // 새 비밀번호 != 비밀번호 확인
    if (!changePassword.equals(changeConfirm)) {
      logger.info("새 비밀번호와 일치하지 않음");
      WebUtil.redirect(response, "/pilot-project/user?action=modifyform");
      return;
    }

    UserDao userDao = UserDao.INSTANCE;
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    if (!userDao.checkPassword(authUser.getId(), SecurityUtil.encryptSHA256(password))) {
      logger.info("비밀번호가 틀렸습니다");
      out.println("<script language=\"javascript\">");
      out.println("alert('비밀번호가 틀렸습니다.'); location.href=\"/pilot-project/user?action=modifyform\"");
      out.println("</script>");
      out.close();
      return;
    }

    // 회원 수정
    authUser.setName(name);
    authUser.setPassword(SecurityUtil.encryptSHA256(password));

    if (changePassword.equals("") || changePassword == null) {
      userDao.update(authUser, "");
    } else {
      userDao.update(authUser, SecurityUtil.encryptSHA256(changePassword));
    }

    // 세션 정보 변경
    authUser.setPassword("");
    session.setAttribute("authUser", authUser);

    WebUtil.redirect(response, "/pilot-project/");
  }

}