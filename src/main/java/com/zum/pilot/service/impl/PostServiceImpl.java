package com.zum.pilot.service.impl;

import com.zum.pilot.dao.PostRepository;
import com.zum.pilot.entity.Post;
import com.zum.pilot.service.PostService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
  @Autowired
  private PostRepository postRepository;

  @Override
  public Page<Post> findAllPostList(Pageable pageable) {
    Post post = postRepository.getTotalPosts();
    return postRepository.findAll(pageable);
  }
}
