package com.zum.pilot.controller;


import com.zum.pilot.constant.UserConstant;
import com.zum.pilot.entity.UserEntity;
import com.zum.pilot.service.UserService;
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
import java.io.PrintWriter;

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
  @RequestMapping(value = "/"+ UserConstant.JOIN, method = RequestMethod.GET)
  public void joinForm() {
    logger.info(UserConstant.JOIN_FORM);
  }

  @RequestMapping(value = "/"+ UserConstant.JOIN, method = RequestMethod.POST)
  public String join(@RequestParam(value = "confirm", defaultValue = "") String confirm, @ModelAttribute UserEntity userVo, BindingResult result, Model model) {
    logger.info(UserConstant.JOIN);

    if (!userVo.getPassword().equals(confirm)) {
      return "redirect:/user/" + UserConstant.JOIN;
    }

    userVo.setPassword(SecurityUtil.encryptSHA256(userVo.getPassword()));
    logger.info("통과");
    userService.create(userVo);

    return "redirect:/user/"+UserConstant.JOIN_SUCCESS;
  }

  @RequestMapping(value = UserConstant.JOIN_SUCCESS)
  public String joinSuccess() {
    logger.info(UserConstant.JOIN_SUCCESS);
    return "user/" + UserConstant.JOIN_SUCCESS;
  }

  // 유효성 검사
  @RequestMapping(value = UserConstant.CHECK_EMAIL)
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

  @RequestMapping(value = UserConstant.CHECK_NAME)
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
  @RequestMapping(value = "/" + UserConstant.LOGIN, method = RequestMethod.POST)
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

  @RequestMapping(value = UserConstant.LOGOUT)
  public String logout(HttpSession session) {
    logger.info(UserConstant.LOGOUT);

    //로그아웃 처리
    session.removeAttribute("authUser");    // 세션 삭제
    session.invalidate();    // 세션 종료

    return "redirect:/";
  }

  // 회원 수정
  @RequestMapping(value = UserConstant.MODIFY, method = RequestMethod.GET)
  public void modifyForm() {
    logger.info(UserConstant.MODIFY_FORM + "[GET]");
  }

  @RequestMapping(value = UserConstant.MODIFY, method = RequestMethod.POST)
  public String modify(
          @ModelAttribute UserEntity userVo,
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

    if (!userService.checkPassword(authUser.getId(), SecurityUtil.encryptSHA256(userVo.getPassword()))) {
      String msg = "비밀번호가 틀렸습니다.";
      String url = "/user/modify";
      return "redirect:/user/modify";
    }

    // 회원 수정
    authUser.setName(userVo.getName());
    authUser.setPassword(SecurityUtil.encryptSHA256(userVo.getPassword()));

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
  @RequestMapping(value = UserConstant.WITHDRAWAL, method = RequestMethod.GET)
  public void withdrawalForm() {
    logger.info(UserConstant.WITHDRAWAL_FORM);
  }

  @RequestMapping(value = UserConstant.WITHDRAWAL, method = RequestMethod.POST)
  public void withdrawal(
          @RequestParam("password") String password,
          HttpSession session,
          HttpServletResponse response) {
    logger.info(UserConstant.WITHDRAWAL);

    // db에서 회원정보 삭제
    UserEntity userVo = (UserEntity) session.getAttribute("authUser");
    password = SecurityUtil.encryptSHA256(password);  // 패스워드 암호화

    response.setContentType("text/html; charset=UTF-8");
    // id, password가 동일한지 체크
    if (!userService.checkPassword(userVo.getId(), password)) {
      String msg = "비밀번호가 틀렸습니다.";
      String url = "/user/withdrawal";
      alertScript(response, msg, url);
      return;
    }

    // 동일하다면 삭제
    userService.delete(userVo.getId(), password);

    //로그아웃 처리
    session.removeAttribute("authUser");    // 세션 삭제
    session.invalidate();    // 세션 종료

    String msg = "회원탈퇴가 완료되었습니다.";
    String url = "/";
    alertScript(response, msg, url);
  }

  private void alertScript(HttpServletResponse response, String msg, String url) {
    response.setContentType("text/html; charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      out.println("<script language=\"javascript\">");
      String alertMsg = "alert('" + msg + "'); location.href=\"" + url + "\"";
      out.println(alertMsg);
      out.println("</script>");
//      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
