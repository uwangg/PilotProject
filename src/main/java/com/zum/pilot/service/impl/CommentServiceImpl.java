package com.zum.pilot.service.impl;

import com.zum.pilot.dao.CommentRepository;
import com.zum.pilot.entity.Comment;
import com.zum.pilot.entity.Post;
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
  public Page<Comment> findAllCommentList(Long postId, PageRequest pageRequest) {
    return commentRepository.findAllByPostId(postId, pageRequest);
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
    List<Comment> comments = commentRepository.findAllByThreadLessThanAndThreadGreaterThan(begin, end);
    for(Comment comment : comments) {
      int thread = comment.getThread() - 1;
      comment.setThread(thread);
      commentRepository.save(comment);
    }
  }

  @Override
  public void writeComment(Comment comment) {
    commentRepository.save(comment);
  }

  @Override
  public void modifyComment(Comment comment) {
    commentRepository.save(comment);
  }

  @Override
  @Transactional
  public void deleteComment(Long commentId) {
    Comment comment = commentRepository.getOne(commentId);
    comment.setDeleteFlag(true);
    commentRepository.save(comment);
  }

  @Override
  public void deleteCommentByUserId(Long userId) {

  }

  @Override
  public void deleteCommentByPostId(Long postId) {

  }
}
