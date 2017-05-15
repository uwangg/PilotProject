package com.zum.pilot.controller;


import com.zum.pilot.action.UserConstant;
import com.zum.pilot.dao.UserDao;
import com.zum.pilot.util.SecurityUtil;
import com.zum.pilot.util.WebUtil;
import com.zum.pilot.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/*")
public class UserController {

  private static final Logger logger =
          LoggerFactory.getLogger(UserController.class);

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
      return "redirect:user/" + UserConstant.JOIN;
    }
    UserDao userDao = UserDao.INSTANCE;

    userVo.setPassword(SecurityUtil.encryptSHA256(userVo.getPassword()));
    logger.info("password = " + userVo.getPassword());
    userDao.insert(userVo);

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
