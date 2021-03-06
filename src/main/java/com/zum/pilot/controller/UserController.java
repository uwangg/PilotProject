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
    logger.debug(UserConstant.JOIN_FORM);
  }

  @RequestMapping(value = "/join", method = RequestMethod.POST)
  public String join(@RequestParam(value = "confirm", defaultValue = "") String confirm, 
                     @ModelAttribute UserEntity userEntity, BindingResult result, Model model) {
    logger.debug(UserConstant.JOIN);

    if(userEntity.getPassword().length() < 6 || confirm.length() < 6) {
      return "redirect:/user/join";
    }
    if (!userEntity.getPassword().equals(confirm)) {
      return "redirect:/user/join";
    }

    userEntity.setPassword(SecurityUtil.encryptSHA256(userEntity.getPassword()));
    userService.createUser(userEntity);

    return "redirect:/user/joinsuccess";
  }

  @RequestMapping(value = "/joinsuccess")
  public String joinSuccess() {
    logger.debug(UserConstant.JOIN_SUCCESS);
    return "user/" + UserConstant.JOIN_SUCCESS;
  }

  // 유효성 검사
  @RequestMapping("/checkemail")
  @ResponseBody
  public boolean checkEmail(@RequestParam("email") String email) {
    logger.debug("checkemail");
    // 이메일 중복체크
     return userService.checkEmail(email);
  }

  @RequestMapping("/checkname")
  @ResponseBody
  public boolean checkName(@RequestParam("name") String name, HttpSession session) {
    logger.debug("checkname");
    UserEntity authUser = (UserEntity) session.getAttribute("authUser");
    // 회원 가입시 닉네임 중복체크
    return userService.checkName(name, authUser);
  }

  // 로그인 & 로그아웃
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
    logger.debug(UserConstant.LOGIN);
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
    logger.debug(UserConstant.LOGOUT);

    //로그아웃 처리
    session.removeAttribute("authUser");    // 세션 삭제
    session.invalidate();    // 세션 종료

    return "redirect:/";
  }

  // 회원 수정
  @RequestMapping(value = "/modify", method = RequestMethod.GET)
  public void modifyForm() {
    logger.debug(UserConstant.MODIFY_FORM + "[GET]");
  }

  @RequestMapping(value = "/modify", method = RequestMethod.POST)
  public String modify(
          String name,
          String password,
          @RequestParam(value = "changePasswd", defaultValue = "") String changePassword,
          @RequestParam(value = "changeConfirm", defaultValue = "") String changeConfirm,
          HttpSession session,
          HttpServletResponse response ) {
    logger.debug(UserConstant.MODIFY + "[POST]");

    UserEntity authUser = (UserEntity) session.getAttribute("authUser");
    password = SecurityUtil.encryptSHA256(password);

    // 새 비밀번호 != 비밀번호 확인
    if (!changePassword.equals(changeConfirm)) {
      return "redirect:/user/modify";
    }
    if (!changePassword.equals("")) {
      if(changePassword.length() < 6 && changeConfirm.length() < 6) {
        return "redirect:/user/modify";
      }
      changePassword = SecurityUtil.encryptSHA256(changePassword);
    }
    
    authUser = userService.modifyUser(authUser.getId(), name, password, changePassword);
    if(authUser == null) {
      String msg = "비밀번호가 올바르지않습니다.";
      String url = "/user/modify";
      ScriptUtil.alert(response, msg, url);
      return "redirect:/user/modify";
    }
    // 세션 정보 변경
    session.setAttribute("authUser", authUser);
    return "redirect:/";
  }

  // 회원탈퇴
  @RequestMapping(value = "/withdrawal", method = RequestMethod.GET)
  public void withdrawalForm() {
    logger.debug(UserConstant.WITHDRAWAL_FORM);
  }

  @RequestMapping(value = "/withdrawal", method = RequestMethod.POST)
  public void withdrawal(
          String password,
          HttpSession session,
          HttpServletResponse response) {
    logger.debug(UserConstant.WITHDRAWAL);

    // db에서 회원정보 삭제
    UserEntity userEntity = (UserEntity) session.getAttribute("authUser");
    password = SecurityUtil.encryptSHA256(password);  // 패스워드 암호화

    String msg;
    String url;
    if(userService.deleteUser(userEntity.getId(), password)) {
      //로그아웃 처리
      session.removeAttribute("authUser");    // 세션 삭제
      session.invalidate();    // 세션 종료
      msg = "회원탈퇴가 완료되었습니다.";
      url = "/";
      ScriptUtil.alert(response, msg, url);
    } else {
      msg = "비밀번호가 틀렸습니다.";
      url = "/user/withdrawal";
      ScriptUtil.alert(response, msg, url);
    }
  }
}
