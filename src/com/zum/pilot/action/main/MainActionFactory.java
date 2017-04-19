package com.zum.pilot.action.main;

import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;

public class MainActionFactory implements ActionFactory {

	@Override
	public Action getAction(String actionName) {
		return new DefaultAction();
	}

}
