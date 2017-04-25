package com.zum.pilot.action.board;

import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;
import com.zum.pilot.action.BoardConstant;

public enum BoardActionFactory implements ActionFactory {
	INSTANCE;
	
	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if(BoardConstant.WRITE_FORM.equals(actionName)) {	// 글쓰기
			action = PostWriteFormAction.INSTANCE;
		} else if(BoardConstant.WRITE.equals(actionName)) {
			action = PostWriteAction.INSTANCE;
		} else if(BoardConstant.VIEW.equals(actionName)) {	// 글보기
			action = PostViewAction.INSTANCE;
		} else if(BoardConstant.MODIFY_FORM.equals(actionName)) {	// 글 수정
			action = PostModifyFormAction.INSTANCE;
		} else if(BoardConstant.MODIFY.equals(actionName)) {
			action = PostModifyAction.INSTANCE;
		} else if(BoardConstant.DELETE.equals(actionName)) {	// 글 삭제
			action = PostDeleteAction.INSTANCE;
		} else if(BoardConstant.COMMENT_WRITE.equals(actionName)) {	// 댓글 쓰기
			action = CommentWriteAction.INSTANCE;
		} else if(BoardConstant.COMMENT_MODIFY.equals(actionName)) {	// 댓글 수정
			action = CommentModifyAction.INSTANCE;
		} else if(BoardConstant.COMMENT_DELETE.equals(actionName)) {	// 댓글 삭제
			action = CommentDeleteAction.INSTANCE;
		}
		else {
			action = DefaultAction.INSTANCE;
		}
		return action;
	}

}
