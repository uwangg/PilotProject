package com.zum.pilot.action.user;

import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;
import com.zum.pilot.action.UserConstant;

public enum UserActionFactory implements ActionFactory {
	INSTANCE;
	
	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if(UserConstant.JOIN_FORM.equals(actionName)) {	// 회원가입
			action = new JoinFormAction();
		} else if(UserConstant.JOIN.equals(actionName)) {
			action = new JoinAction();
		} else if(UserConstant.CHECK_EMAIL.equals(actionName)) {	// 유효성 검사
			action = new CheckEmailAction();
		} else if(UserConstant.CHECK_NAME.equals(actionName)) {
			action = new CheckNameAction();
		} else if(UserConstant.JOIN_SUCCESS.equals(actionName)) {
			action = new JoinSuccessAction();
		} else if(UserConstant.LOGIN.equals(actionName)) {	// 로그인
			action = new LoginAction();
		} else if(UserConstant.LOGOUT.equals(actionName)) {	// 로그아웃
			action = new LogoutAction();
		} else if(UserConstant.MODIFY_FORM.equals(actionName)) {	// 회원수정
			action = new ModifyFormAction();
		} else if(UserConstant.MODIFY.equals(actionName)) {
			action = new ModifyAction();
		} else if(UserConstant.WITHDRAWAL_FORM.equals(actionName)) {	// 회원탈퇴
			action = new WithdrawalFormAction();
		} else if(UserConstant.WITHDRWAL.equals(actionName)) {
			action = new WithdrawalAction();
		} else {
			action = new DefaultAction();
		}
		return action;
	}

}
