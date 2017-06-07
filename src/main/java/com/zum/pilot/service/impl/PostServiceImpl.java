package com.zum.pilot.service.impl;

import com.zum.pilot.repository.PostRepository;
import com.zum.pilot.entity.PostEntity;
import com.zum.pilot.service.CommentService;
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

  @Autowired
  private CommentService commentService;

  // 게시글 페이지네이션
  @Override
  public Page<PostEntity> findAllPostList(Pageable pageable) {
    Page<PostEntity> posts = postRepository.findAllByDeleteFlag(false, pageable);
    return posts;
  }

  @Override
  @Transactional
  public PostEntity readPost(Long id) {;
    // 게시글 불러오기
    PostEntity postEntity = postRepository.findOne(id);
    // 게시글 조회수 증가
    Long hit = postEntity.getHit() + 1;
    postEntity.setHit(hit);
    // 게시글 업데이트
    return postRepository.save(postEntity);
  }

  @Override
  public PostEntity getPost(Long id) {
    return postRepository.findByIdAndDeleteFlag(id, false);
  }

  @Override
  public void create(PostEntity vo) {
    postRepository.save(vo);
  }

  @Override
  @Transactional
  public void modifyPost(Long postId, String title, String content, String imagePath) {
    PostEntity postEntity = postRepository.findByIdAndDeleteFlag(postId, false);
    postEntity.setTitle(title);
    postEntity.setContent(content);
    if(imagePath == null)
      imagePath = "";
    postEntity.setImagePath(imagePath);
    postRepository.save(postEntity);
  }

  public void modifyPost(PostEntity postEntity) {
    postRepository.save(postEntity);
  }

  @Override
  @Transactional
  public void deletePost(Long postId) {
    PostEntity postEntity = postRepository.findByIdAndDeleteFlag(postId, false);
    postEntity.setDeleteFlag(true);
    // 게시글 삭제
    postRepository.save(postEntity);
    commentService.deleteCommentByPostId(postId);
  }

  @Override
  @Transactional
  public void deleteByUserId(Long userId) {
    List<PostEntity> posts = postRepository.findAllByUserEntityIdAndDeleteFlag(userId);
    for(PostEntity postEntity : posts) {
      postEntity.setDeleteFlag(true);
      postRepository.save(postEntity);
    }
  }
}
