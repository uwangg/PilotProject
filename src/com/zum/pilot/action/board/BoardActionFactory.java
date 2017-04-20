package com.zum.pilot.action.board;

import com.zum.pilot.action.Action;
import com.zum.pilot.action.ActionFactory;
import com.zum.pilot.action.BoardConstant;

public class BoardActionFactory implements ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		if(BoardConstant.WRITE_FORM.equals(actionName)) {	// 글쓰기
			action = new PostWriteFormAction();
		} else if(BoardConstant.WRITE.equals(actionName)) {
			action = new PostWriteAction();
		} else if(BoardConstant.VIEW.equals(actionName)) {	// 글보기
			action = new PostViewAction();
		} else if(BoardConstant.MODIFY_FORM.equals(actionName)) {	// 글 수정
			action = new PostModifyFormAction();
		} else if(BoardConstant.MODIFY.equals(actionName)) {
			action = new PostModifyAction();
		} else if(BoardConstant.DELETE.equals(actionName)) {	// 글 삭제
			action = new PostDeleteAction();
		} else if(BoardConstant.COMMENT_WRITE.equals(actionName)) {	// 댓글 쓰기
			action = new CommentWriteAction();
		} else if(BoardConstant.COMMENT_MODIFY.equals(actionName)) {	// 댓글 수정
			action = new CommentModifyAction();
		} else if(BoardConstant.COMMENT_DELETE.equals(actionName)) {	// 댓글 삭제
			action = new CommentDeleteAction();
		}
		else {
			action = new DefaultAction();
		}
		return action;
	}

}
