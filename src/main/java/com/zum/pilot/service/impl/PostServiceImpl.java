package com.zum.pilot.service.impl;

import com.zum.pilot.dao.PostRepository;
import com.zum.pilot.entity.Post;
import com.zum.pilot.entity.User;
import com.zum.pilot.service.PostService;
import javafx.geometry.Pos;
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
  @Transactional
  public Page<Post> findAllPostList(Pageable pageable) {
    Post post = postRepository.getTotalPosts();
    Page<Post> posts =postRepository.findAll(pageable);
    return posts;
  }

  @Override
  public Post readPost(Long id) {
//    Post post = dao.getPost(postId);
//    post.read();
//    Post.read {
//      readCnt ++;
//      if (readCnt > threshold) bestPost = true;
//    }
//    dao.update(post);
//    return post;
    // 게시글 불러오기
    // 게시글 조회수 증가
    // 게시글 업데이트
    return null;
  }

  @Override
  public Post getPost(Long number) {
    return null;
  }

  @Override
  public void create(Post vo) {
    postRepository.save(vo);
  }

  @Override
  public void update(Post vo) {
    postRepository.save(vo);
  }

  @Override
  public void delete(Long postId, Long userId) {
    Post post = new Post();
    post.setId(postId);
    User user = new User();
    user.getId();
    post.setUser(user);
    // 게시글 삭제
    postRepository.save(post);
  }

  @Override
  public void deleteByUserId(Long userId) {

  }
}
