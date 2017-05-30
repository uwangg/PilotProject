package com.zum.pilot.service;

import com.zum.pilot.entity.Comment;
import com.zum.pilot.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CommentService {
  Page<Comment> findAllCommentList(Long postId, PageRequest pageRequest);
}
