package com.zum.pilot.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;
import com.zum.pilot.action.user.UserActionFactory;

@WebServlet("/user2")
public class UserController2 extends HttpServlet {
  private static final long serialVersionUID = 1L;


  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doAction(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doAction(request, response);
  }

  protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String actionName = request.getParameter("action");
    ActionFactory actionFactory = UserActionFactory.INSTANCE;
    Action action = actionFactory.getAction(actionName);
    action.execute(request, response);
  }
}
