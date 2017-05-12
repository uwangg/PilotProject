package com.zum.pilot.action.user;

import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;
import com.zum.pilot.action.UserConstant;

public enum UserActionFactory implements ActionFactory {
  INSTANCE;

  @Override
  public Action getAction(String actionName) {
    Action action = null;
    if (UserConstant.JOIN_FORM.equals(actionName)) {    // 회원가입
      action = JoinFormAction.INSTANCE;
    } else if (UserConstant.JOIN.equals(actionName)) {
      action = JoinAction.INSTANCE;
    } else if (UserConstant.CHECK_EMAIL.equals(actionName)) {    // 유효성 검사
      action = CheckEmailAction.INSTANCE;
    } else if (UserConstant.CHECK_NAME.equals(actionName)) {
      action = CheckNameAction.INSTANCE;
    } else if (UserConstant.JOIN_SUCCESS.equals(actionName)) {
      action = JoinSuccessAction.INSTANCE;
    } else if (UserConstant.LOGIN.equals(actionName)) {    // 로그인
      action = LoginAction.INSTANCE;
    } else if (UserConstant.LOGOUT.equals(actionName)) {    // 로그아웃
      action = LogoutAction.INSTANCE;
    } else if (UserConstant.MODIFY_FORM.equals(actionName)) {    // 회원수정
      action = ModifyFormAction.INSTANCE;
    } else if (UserConstant.MODIFY.equals(actionName)) {
      action = ModifyAction.INSTANCE;
    } else if (UserConstant.WITHDRAWAL_FORM.equals(actionName)) {    // 회원탈퇴
      action = WithdrawalFormAction.INSTANCE;
    } else if (UserConstant.WITHDRAWAL.equals(actionName)) {
      action = WithdrawalAction.INSTANCE;
    } else {
      action = DefaultAction.INSTANCE;
    }
    return action;
  }

}
