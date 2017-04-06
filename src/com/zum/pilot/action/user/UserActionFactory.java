package com.zum.pilot.action.user;

import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;

public class UserActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("joinform".equals(actionName)) {	// 회원가입
			action = new JoinFormAction();
		} else if("join".equals(actionName)) {
			action = new JoinAction();
		} else if("joinsuccess".equals(actionName)) {
			action = new JoinSuccessAction();
		} else if("login".equals(actionName)) {	// 로그인
			action = new LoginAction();
		} else if("logout".equals(actionName)) {	// 로그아웃
			action = new LogoutAction();
		} else if("modifyform".equals(actionName)) {	// 회원수정
			action = new ModifyFormAction();
		} else if("modify".equals(actionName)) {
			action = new ModifyAction();
		} else if("withdrawalform".equals(actionName)) {	// 회원탈퇴
			action = new WithdrawalFormAction();
		} else if("withdrawal".equals(actionName)) {
			action = new WithdrawalAction();
		} else {
			action = new DefaultAction();
		}
		return action;
	}

}
