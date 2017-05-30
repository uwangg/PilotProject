package com.zum.pilot.service.impl;

import com.zum.pilot.dao.PostRepository;
import com.zum.pilot.entity.Post;
import com.zum.pilot.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
  @Autowired
  private PostRepository postRepository;

  // 게시글 페이지네이션
  @Override
  public Page<Post> findAllPostList(Pageable pageable) {
    Page<Post> posts = postRepository.findAllByDeleteFlag(false, pageable);
    return posts;
  }

  @Override
  public Post readPost(Long id) {;
    // 게시글 불러오기
    Post post = postRepository.findOne(id);
    // 게시글 조회수 증가
    Long hit = post.getHit() + 1;
    post.setHit(hit);
    // 게시글 업데이트
    return postRepository.save(post);
  }

  @Override
  public Post getPost(Long id) {
    return postRepository.findByIdAndDeleteFlag(id, false);
  }

  @Override
  public void create(Post vo) {
    postRepository.save(vo);
  }

  @Override
  @Transactional
  public void modifyPost(Long postId, String title, String content, String imagePath) {
    Post post = postRepository.findByIdAndDeleteFlag(postId, false);
    post.setTitle(title);
    post.setContent(content);
    if(imagePath == null)
      imagePath = "";
    post.setImagePath(imagePath);
    postRepository.save(post);
  }

  @Override
  @Transactional
  public void deletePost(Long postId) {
    Post post = postRepository.findByIdAndDeleteFlag(postId, false);
    post.setDeleteFlag(true);
    // 게시글 삭제
    postRepository.save(post);
  }

  @Override
  @Transactional
  public void deleteByUserId(Long userId) {
    List<Post> posts = postRepository.findAllByUserIdAndDeleteFlag(userId, false);
    for(Post post : posts) {
      post.setDeleteFlag(true);
      postRepository.save(post);
    }
  }
}
