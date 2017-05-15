package com.zum.pilot.controller;


import com.zum.pilot.action.UserConstant;
import com.zum.pilot.dao.UserDao;
import com.zum.pilot.util.SecurityUtil;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/*")
public class UserController {

  private static final Logger logger =
          LoggerFactory.getLogger(UserController.class);

  // 회원가입
  @RequestMapping(value = UserConstant.JOIN, method = RequestMethod.GET)
  public void joinForm() {
    logger.info(UserConstant.JOIN_FORM);
//    return "user/" + UserConstant.JOIN;
  }

  @RequestMapping(value = UserConstant.JOIN, method = RequestMethod.POST)
  public String join(@ModelAttribute UserVo vo) {
    logger.info(UserConstant.JOIN);

//    String name = request.getParameter("name");
//    String email = request.getParameter("email");
//    String passwd = request.getParameter("passwd");
//    String confirm = request.getParameter("confirm");
//
//    if (!passwd.equals(confirm)) {
//      return "redirect:user/" + UserConstant.JOIN_FORM;
//    }
//
//    UserDao userDao = UserDao.INSTANCE;
//
//    UserVo userVo = new UserVo(email, name, SecurityUtil.encryptSHA256(passwd));
//    userDao.insert(userVo);

    return "redirect:/user/"+UserConstant.JOIN_SUCCESS;
  }

  @RequestMapping(value = UserConstant.JOIN_SUCCESS)
  public String joinSuccess() {
    logger.info(UserConstant.JOIN_SUCCESS);
    return "user/" + UserConstant.JOIN_SUCCESS;
  }

  // 유효성 검사
  @RequestMapping(value = UserConstant.CHECK_EMAIL)
  public void checkEmail() {
    logger.info(UserConstant.CHECK_EMAIL);
  }

  @RequestMapping(value = UserConstant.CHECK_NAME)
  public void checkName() {
    logger.info(UserConstant.CHECK_NAME);
  }

  // 로그인 & 로그아웃
  @RequestMapping(value = UserConstant.LOGIN)
  public void login() {
    logger.info(UserConstant.LOGIN);
  }

  @RequestMapping(value = UserConstant.LOGOUT)
  public void logout() {
    logger.info(UserConstant.LOGOUT);
  }

  // 회원 수정
  @RequestMapping(value = UserConstant.MODIFY_FORM)
  public void modifyForm() {
    logger.info(UserConstant.MODIFY_FORM);
  }

  @RequestMapping(value = UserConstant.MODIFY)
  public void modify() {
    logger.info(UserConstant.MODIFY);
  }

  // 회원탈퇴
  @RequestMapping(value = UserConstant.WITHDRAWAL_FORM)
  public void withdrawalForm() {
    logger.info(UserConstant.WITHDRAWAL_FORM);
  }

  @RequestMapping(value = UserConstant.WITHDRAWAL)
  public void withdrawal() {
    logger.info(UserConstant.WITHDRAWAL);
  }
}
