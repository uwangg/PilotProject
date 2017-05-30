package com.zum.pilot.service.impl;

import com.zum.pilot.dao.CommentRepository;
import com.zum.pilot.entity.Comment;
import com.zum.pilot.entity.Post;
import com.zum.pilot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
  @Autowired
  private CommentRepository commentRepository;

  @Override
  public Page<Comment> findAllCommentList(Long postId, PageRequest pageRequest) {
    return commentRepository.findAllByPostId(postId, pageRequest);
  }
}
