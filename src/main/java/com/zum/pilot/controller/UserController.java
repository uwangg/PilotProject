package com.zum.pilot.controller;


import com.zum.pilot.constant.UserConstant;
import com.zum.pilot.entity.UserEntity;
import com.zum.pilot.service.UserService;
import com.zum.pilot.util.ScriptUtil;
import com.zum.pilot.util.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

  private static final Logger logger =
          LoggerFactory.getLogger(UserController.class);

  @RequestMapping("")
  public String main() {
    return "redirect:/";
  }

  // 회원가입
  @RequestMapping(value = "/join", method = RequestMethod.GET)
  public void joinForm() {
    logger.info(UserConstant.JOIN_FORM);
  }

  @RequestMapping(value = "/join", method = RequestMethod.POST)
  public String join(@RequestParam(value = "confirm", defaultValue = "") String confirm, 
                     @ModelAttribute UserEntity userEntity, BindingResult result, Model model) {
    logger.info(UserConstant.JOIN);

    if (!userEntity.getPassword().equals(confirm)) {
      return "redirect:/user/" + UserConstant.JOIN;
    }

    userEntity.setPassword(SecurityUtil.encryptSHA256(userEntity.getPassword()));
    logger.info("통과");
    userService.create(userEntity);

    return "redirect:/user/joinsuccess";
  }

  @RequestMapping(value = "/joinsuccess")
  public String joinSuccess() {
    logger.info(UserConstant.JOIN_SUCCESS);
    return "user/" + UserConstant.JOIN_SUCCESS;
  }

  // 유효성 검사
  @RequestMapping("/checkemail")
  @ResponseBody
  public boolean checkEmail(@RequestParam("email") String email) {
    logger.info(UserConstant.CHECK_EMAIL);

    // 이메일 중복체크
    if(userService.checkEmail(email)) {
      return false;
    } else {
      return true;
    }
  }

  @RequestMapping("/checkname")
  @ResponseBody
  public boolean checkName(@RequestParam("name") String name, HttpSession session) {
    logger.info(UserConstant.CHECK_NAME);

    // 회원 수정시 닉네임 중복체크
    UserEntity authUser = (UserEntity) session.getAttribute("authUser");
    if (authUser != null) {
      if (authUser.getName().equals(name)) {
        logger.info("현재 유저명과 같음");
        return true;
      }
    }

    // 회원 가입시 닉네임 중복체크
    if (userService.checkName(name)) {
      logger.info("닉네임 중복입니다.");
      return false; // id 중복
    } else {
      logger.info("가능한 닉네임");
      return true;
    }
  }

  // 로그인 & 로그아웃
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
    logger.info(UserConstant.LOGIN);
    password = SecurityUtil.encryptSHA256(password);
    // 유저정보 가져오기
    UserEntity authUser = userService.checkEmailAndPassword(email, password);
    // 로그인성공시
    if (authUser != null) {
      // 인증 성공 (로그인처리)
      session.setAttribute("authUser", authUser);
    }
    return "redirect:/";
  }

  @RequestMapping("/logout")
  public String logout(HttpSession session) {
    logger.info(UserConstant.LOGOUT);

    //로그아웃 처리
    session.removeAttribute("authUser");    // 세션 삭제
    session.invalidate();    // 세션 종료

    return "redirect:/";
  }

  // 회원 수정
  @RequestMapping(value = "/modify", method = RequestMethod.GET)
  public void modifyForm() {
    logger.info(UserConstant.MODIFY_FORM + "[GET]");
  }

  @RequestMapping(value = "/modify", method = RequestMethod.POST)
  public String modify(
          @ModelAttribute UserEntity userEntity,
          @RequestParam(value = "changePasswd", defaultValue = "") String changePassword,
          @RequestParam(value = "changeConfirm", defaultValue = "") String changeConfirm,
          HttpSession session,
          HttpServletResponse response ) {
    logger.info(UserConstant.MODIFY + "[POST]");

    UserEntity authUser = (UserEntity) session.getAttribute("authUser");

    // 새 비밀번호 != 비밀번호 확인
    if (!changePassword.equals(changeConfirm)) {
      logger.info("새 비밀번호와 일치하지 않음");
      return "redirect:/user/modify";
    }

    if (!userService.checkPassword(authUser.getId(), SecurityUtil.encryptSHA256(userEntity.getPassword()))) {
      String msg = "비밀번호가 틀렸습니다.";
      String url = "/user/modify";
      ScriptUtil.alert(response, msg, url);
      return "redirect:/user/modify";
    }

    // 회원 수정
    authUser.setName(userEntity.getName());
    authUser.setPassword(SecurityUtil.encryptSHA256(userEntity.getPassword()));

    if (!changePassword.equals("")) {
      authUser.setPassword(SecurityUtil.encryptSHA256(changePassword));
    }
    userService.update(authUser);

    // 세션 정보 변경
    authUser.setPassword("");
    session.setAttribute("authUser", authUser);

    return "redirect:/";
  }

  // 회원탈퇴
  @RequestMapping(value = "/withdrawal", method = RequestMethod.GET)
  public void withdrawalForm() {
    logger.info(UserConstant.WITHDRAWAL_FORM);
  }

  @RequestMapping(value = "/withdrawal", method = RequestMethod.POST)
  public void withdrawal(
          @RequestParam("password") String password,
          HttpSession session,
          HttpServletResponse response) {
    logger.info(UserConstant.WITHDRAWAL);

    // db에서 회원정보 삭제
    UserEntity userEntity = (UserEntity) session.getAttribute("authUser");
    password = SecurityUtil.encryptSHA256(password);  // 패스워드 암호화

    response.setContentType("text/html; charset=UTF-8");
    // id, password가 동일한지 체크
    if (!userService.checkPassword(userEntity.getId(), password)) {
      String msg = "비밀번호가 틀렸습니다.";
      String url = "/user/withdrawal";
      ScriptUtil.alert(response, msg, url);
      return;
    }

    // 동일하다면 삭제
    userService.delete(userEntity.getId(), password);

    //로그아웃 처리
    session.removeAttribute("authUser");    // 세션 삭제
    session.invalidate();    // 세션 종료

    String msg = "회원탈퇴가 완료되었습니다.";
    String url = "/";
    ScriptUtil.alert(response, msg, url);
  }

}
