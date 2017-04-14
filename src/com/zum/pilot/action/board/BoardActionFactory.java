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
		} else if("view".equals(actionName)) {	// 글보기
			action = new PostViewAction();
		} else if("modifyform".equals(actionName)) {	// 글 수정
			action = new PostModifyFormAction();
		} else if("modify".equals(actionName)) {
			action = new PostModifyAction();
		} else if("delete".equals(actionName)) {	// 글 삭제
			action = new PostDeleteAction();
		} else if("commentwrite".equals(actionName)) {	// 댓글 쓰기
			action = new CommentWriteAction();
		} else if("commentmodify".equals(actionName)) {	// 댓글 수정
			action = new CommentModifyAction();
		} else if("commentdelete".equals(actionName)) {	// 댓글 삭제
			action = new CommentDeleteAction();
		}
		else {
			action = new DefaultAction();
		}
		return action;
	}

}
