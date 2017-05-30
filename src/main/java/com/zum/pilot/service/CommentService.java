package com.zum.pilot.service;

import com.zum.pilot.entity.Comment;
import com.zum.pilot.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CommentService {
  Page<Comment> findAllCommentList(Long postId, PageRequest pageRequest);

  public int getMaxThread(Long postId);
  public void updateThread(int begin, int end);
  public void writeComment(Comment comment);
  public void modifyComment(Comment comment);
  public void deleteComment(Long commentId);
  public void deleteCommentByUserId(Long userId);
  public void deleteCommentByPostId(Long postId);
}
