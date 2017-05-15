package com.zum.pilot.controller;


import com.zum.pilot.action.UserConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/*")
public class UserController {

  private static final Logger logger =
          LoggerFactory.getLogger(UserController.class);

  // 회원가입
  @RequestMapping(value = UserConstant.JOIN_FORM, method = RequestMethod.GET)
  public String joinForm() {
    logger.info("joinform");
    return "user/join";
  }

  @RequestMapping(value = UserConstant.JOIN)
  public void join() {
    logger.info("join");
  }

  @RequestMapping(value = UserConstant.JOIN_SUCCESS)
  public void joinSuccess() {
    logger.info("joinSuccess");
  }

  // 유효성 검사
  @RequestMapping(value = UserConstant.CHECK_EMAIL)
  public void checkEmail() {

  }

  @RequestMapping(value = UserConstant.CHECK_NAME)
  public void checkName() {
    logger.info("checkName");
  }

  // 로그인 & 로그아웃
  @RequestMapping(value = UserConstant.LOGIN)
  public void login() {
    logger.info("login");
  }

  @RequestMapping(value = UserConstant.LOGOUT)
  public void logout() {
    logger.info("logout");
  }

  // 회원 수정
  @RequestMapping(value = UserConstant.MODIFY_FORM)
  public void modifyForm() {
    logger.info("modifyForm");
  }

  @RequestMapping(value = UserConstant.MODIFY)
  public void modify() {
    logger.info("modify");
  }

  // 회원탈퇴
  @RequestMapping(value = UserConstant.WITHDRAWAL_FORM)
  public void withdrawalForm() {
    logger.info("withdrawalForm");
  }

  @RequestMapping(value = UserConstant.WITHDRAWAL)
  public void withdrawal() {
    logger.info("withdrawal");
  }
}
