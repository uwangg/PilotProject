package com.zum.pilot.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.pilot.action.Action;
import com.zum.pilot.dao.UserDao;
import com.zum.pilot.util.SecurityUtil;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.UserVo;

public enum JoinAction implements Action {
  INSTANCE;

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String passwd = request.getParameter("passwd");
    String confirm = request.getParameter("confirm");

    if (!passwd.equals(confirm)) {
      WebUtil.redirect(response, "/pilot-project/user?action=joinform");
      return;
    }

    UserDao userDao = UserDao.INSTANCE;

    UserVo userVo = new UserVo(email, name, SecurityUtil.encryptSHA256(passwd));
    userDao.insert(userVo);
    WebUtil.redirect(response, "/pilot-project/user?action=joinsuccess");
  }

}
