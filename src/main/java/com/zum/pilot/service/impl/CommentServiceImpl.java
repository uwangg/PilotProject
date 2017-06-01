package com.zum.pilot.service.impl;

import com.zum.pilot.repository.CommentRepository;
import com.zum.pilot.entity.CommentEntity;
import com.zum.pilot.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
  private final static Logger logger =
          LoggerFactory.getLogger(CommentServiceImpl.class);

  @Autowired
  private CommentRepository commentRepository;

  @Override
  public CommentEntity getComment(Long commentId) {
    return commentRepository.findByIdAndDeleteFlag(commentId, false);
  }

  @Override
  public Page<CommentEntity> findAllCommentList(Long postId, PageRequest pageRequest) {
    return commentRepository.findAllByPostEntityId(postId, pageRequest);
  }

  @Override
  public int getMaxThread(Long postId) {
    Integer maxThread = commentRepository.getMaxThread(postId);
    if(maxThread == null)
      maxThread = 0;
    return maxThread;
  }

  @Override
  @Transactional
  public void updateThread(int begin, int end) {
    List<CommentEntity> comments = commentRepository.findAllByThreadLessThanAndThreadGreaterThan(begin, end);
    for(CommentEntity commentEntity : comments) {
      int thread = commentEntity.getThread() - 1;
      commentEntity.setThread(thread);
      commentRepository.save(commentEntity);
    }
  }

  @Override
  public void writeComment(CommentEntity commentEntity) {
    commentRepository.save(commentEntity);
  }

  @Override
  public void modifyComment(CommentEntity commentEntity) {
    commentRepository.save(commentEntity);
  }

  @Override
  @Transactional
  public void deleteComment(Long commentId) {
    CommentEntity commentEntity = commentRepository.getOne(commentId);
    commentEntity.setDeleteFlag(true);
    commentRepository.save(commentEntity);
  }

  @Override
  public void deleteCommentByUserId(Long userId) {
    List<CommentEntity> comments = commentRepository.findAllByUserEntityIdAndDeleteFlag(userId);
    for(CommentEntity commentEntity : comments) {
      commentEntity.setDeleteFlag(true);
      commentRepository.save(commentEntity);
    }
  }

  @Override
  public void deleteCommentByPostId(Long postId) {
    List<CommentEntity> comments = commentRepository.findAllByUserEntityIdAndDeleteFlag(postId);
    for(CommentEntity commentEntity : comments) {
      commentEntity.setDeleteFlag(true);
      commentRepository.save(commentEntity);
    }
  }
}
