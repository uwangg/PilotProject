package com.zum.pilot.service;

import com.zum.pilot.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CommentService {
  Page<CommentEntity> findAllCommentList(Long postId, PageRequest pageRequest);

  CommentEntity getComment(Long commentId);
  int getMaxThread(Long postId);
  void updateThread(int begin, int end);
  void writeComment(CommentEntity commentEntity);
  void modifyComment(CommentEntity commentEntity);
  void deleteComment(Long commentId);
  void deleteCommentByUserId(Long userId);
  void deleteCommentByPostId(Long postId);
}
