package com.zum.pilot.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;
import com.zum.pilot.action.board.BoardActionFactory;

@WebServlet("/board")
public class BoardController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public BoardController() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doAction(request, response);
  }


  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doAction(request, response);
  }

  protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String actionName = request.getParameter("action");
    ActionFactory actionFactory = BoardActionFactory.INSTANCE;
    Action action = actionFactory.getAction(actionName);
    action.execute(request, response);
  }
}