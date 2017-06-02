package com.zum.pilot.service;

import com.zum.pilot.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CommentService {
  Page<CommentEntity> findAllCommentList(Long postId, PageRequest pageRequest);
  void writeComment(CommentEntity commentEntity, int depth, int thread);
  void modifyComment(Long commentId, Long userId, String content);
  void deleteComment(Long commentId, Long userId);
  void deleteCommentByUserId(Long userId);
  void deleteCommentByPostId(Long postId);
}
