package com.zum.pilot.controller;


import com.zum.pilot.constant.UserConstant;
import com.zum.pilot.service.UserService2;
import com.zum.pilot.util.SecurityUtil;
import com.zum.pilot.entity.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService2 userService2;

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
  public String join(@RequestParam(value = "confirm", defaultValue = "") String confirm, @ModelAttribute UserVo userVo, BindingResult result, Model model) {
    logger.info(UserConstant.JOIN);
//    if(result.hasErrors()) {  // name, email, passwd, confirm이 빈칸인지 아닌지 검사
//      model.addAttribute(result.getModel());
//      model.addAttribute("userVo", userVo);
//      return "user/" + UserConstant.JOIN;
//    }
    if (!userVo.getPassword().equals(confirm)) {
      return "redirect:/user/" + UserConstant.JOIN;
    }

    userVo.setPassword(SecurityUtil.encryptSHA256(userVo.getPassword()));
    logger.info("통과");
    userService2.insert(userVo);

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
  public String checkEmail(@RequestParam("email") String email) {
    logger.info(UserConstant.CHECK_EMAIL);

    // 이메일 중복체크
    if(userService2.checkEmail(email)) {
      return "false";
    } else {
      return "true";
    }
  }

  @RequestMapping(value = UserConstant.CHECK_NAME)
  @ResponseBody
  public String checkName(@RequestParam("name") String name, HttpSession session) {
    logger.info(UserConstant.CHECK_NAME);

    // 회원 수정시 닉네임 중복체크
    UserVo authUser = (UserVo) session.getAttribute("authUser");
    if (authUser != null) {
      if (authUser.getName().equals(name)) {
        return "true";
      }
    }

    // 회원 가입시 닉네임 중복체크
    if (userService2.checkName(name)) {
      return "false"; // id 중복
    } else {
      return "true";
    }
  }

  // 로그인 & 로그아웃
  @RequestMapping(value = "/" + UserConstant.LOGIN, method = RequestMethod.POST)
  public void login() {
    logger.info(UserConstant.LOGIN);
  }

  @RequestMapping(value = UserConstant.LOGOUT)
  public void logout() {
    logger.info(UserConstant.LOGOUT);
  }

  // 회원 수정
  @RequestMapping(value = UserConstant.MODIFY, method = RequestMethod.GET)
  public void modifyForm() {
    logger.info(UserConstant.MODIFY_FORM + "[GET]");
  }

  @RequestMapping(value = UserConstant.MODIFY, method = RequestMethod.POST)
  public String modify(
          @ModelAttribute UserVo userVo,
          @RequestParam(value = "changePasswd", defaultValue = "") String changePassword,
          @RequestParam(value = "changeConfirm", defaultValue = "") String changeConfirm,
          HttpSession session,
          HttpServletResponse response ) {
    logger.info(UserConstant.MODIFY + "[POST]");

    UserVo authUser = (UserVo) session.getAttribute("authUser");

    // 새 비밀번호 != 비밀번호 확인
    if (!changePassword.equals(changeConfirm)) {
      logger.info("새 비밀번호와 일치하지 않음");
      return "redirect:/user/modify";
    }

    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = null;
    try {
      out = response.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (!userService2.checkPassword(authUser.getId(), SecurityUtil.encryptSHA256(userVo.getPassword()))) {
      logger.info("비밀번호가 틀렸습니다");
      out.println("<script language=\"javascript\">");
      out.println("alert('비밀번호가 틀렸습니다.'); location.href=\"/pilot-project/user/modify\"");
      out.println("</script>");
      out.close();
      return "redirect:/user/modify";
    }
    // 회원 수정
    authUser.setName(userVo.getName());
    authUser.setPassword(SecurityUtil.encryptSHA256(userVo.getPassword()));

    if (changePassword.equals("")) {
      userService2.update(authUser, "");
    } else {
      userService2.update(authUser, SecurityUtil.encryptSHA256(changePassword));
    }

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
    UserVo userVo = (UserVo) session.getAttribute("authUser");
    password = SecurityUtil.encryptSHA256(password);  // 패스워드 암호화

    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = null;
    try {
      out = response.getWriter();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // id, password가 동일한지 체크
    if (!userService2.checkPassword(userVo.getId(), password)) {
      logger.info("비밀번호 틀림");
      out.println("<script language=\"javascript\">");
      out.println("alert('비밀번호가 틀렸습니다.'); location.href=\"/pilot-project/user/withdrawal\"");
      out.println("</script>");
      out.close();
      return;
    }

    // 동일하다면 삭제
    userService2.delete(userVo.getId(), password);

    //로그아웃 처리
    session.removeAttribute("authUser");    // 세션 삭제
    session.invalidate();    // 세션 종료

    out.println("<script language=\"javascript\">");
    out.println("alert('회원탈퇴가 완료되었습니다.'); location.href=\"/pilot-project/\"");
    out.println("</script>");
    out.close();
  }
}
