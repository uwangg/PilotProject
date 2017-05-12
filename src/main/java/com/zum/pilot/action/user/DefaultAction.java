package com.zum.pilot.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.pilot.action.Action;
import com.zum.pilot.util.WebUtil;

public enum DefaultAction implements Action {
  INSTANCE;

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    WebUtil.redirect(response, "/pilot-project/");
  }

}
