package com.zum.pilot.action.board;

import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if("writeform".equals(actionName)) {	// 글쓰기
			action = new PostWriteFormAction();
		} else if("write".equals(actionName)) {
			action = new PostWriteAction();
		} else if("view".equals(actionName)) {
			action = new PostViewAction();
		} else if("modifyform".equals(actionName)) {
			action = new PostModifyFormAction();
		} else if("modify".equals(actionName)) {
			action = new PostModifyAction();
		} else if("delete".equals(actionName)) {
			action = new PostDeleteAction();
		}
		else {
			action = new DefaultAction();
		}
		return action;
	}

}
