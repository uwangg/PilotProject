package com.zum.pilot.action.user;

import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;

public class UserActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if(actionName.equals("joinform")) {	// 회원가입
			action = new JoinFormAction();
		} else if(actionName.equals("join")) {
			action = new JoinAction();
		} else if(actionName.equals("joinsuccess")) {
			action = new JoinSuccessAction();
		} else if(actionName.equals("login")) {	// 로그인
			action = new LoginAction();
		} else if(actionName.equals("logout")) {	// 로그아웃
			action = new LogoutAction();
		} else if(actionName.equals("modifyform")) {	// 회원수정
			action = new ModifyFormAction();
		} else if(actionName.equals("modify")) {
			action = new ModifyAction();
		} else if(actionName.equals("withdrawalform")) {	// 회원탈퇴
			action = new WithdrawalFormAction();
		} else if(actionName.equals("withdrawal")) {
			action = new WithdrawalAction();
		}
		return action;
	}

}
