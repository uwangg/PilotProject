package com.zum.pilot.action.user;

import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;

public class UserActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if(actionName.equals("joinform")) {
			action = new JoinFormAction();
		} else if(actionName.equals("join")) {
			action = new JoinAction();
		} else if(actionName.equals("joinsuccess")) {
			action = new JoinSuccessAction();
		} else if(actionName.equals("login")) {
			action = new LoginAction();
		} 
		return action;
	}

}
