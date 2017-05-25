package com.zum.pilot.service;

import com.zum.pilot.dao.PostRepository;
import com.zum.pilot.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService2Impl implements PostService2 {
  @Autowired
  PostRepository postRepository;

  @Override
  public PostVo findPostById(Long id) {
    return postRepository.findOne(id);
  }
}
