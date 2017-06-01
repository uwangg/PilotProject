package com.zum.pilot.service;

import com.zum.pilot.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CommentService {
  Page<Comment> findAllCommentList(Long postId, PageRequest pageRequest);

  Comment getComment(Long commentId);
  int getMaxThread(Long postId);
  void updateThread(int begin, int end);
  void writeComment(Comment comment);
  void modifyComment(Comment comment);
  void deleteComment(Long commentId);
  void deleteCommentByUserId(Long userId);
  void deleteCommentByPostId(Long postId);
}
