package com.zum.pilot.service;

import com.zum.pilot.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface PostService {
//  Pagination<PostVo> viewPage(int currentPage);
  Page<Post> findAllPostList(Pageable pageable);  // 게시글 불러오기

  // 게시글 읽기
  @Transactional
  Post readPost(Long id);

  Post getPost(Long id); // 게시글 번호에 맞는 게시물 정보 가져오기

  void create(Post vo); // 게시글 등록

  void modifyPost(Long postId, String title, String content, String imagePath); // 게시글 수정

  void deletePost(Long postId); // 게시글 삭제

  void deleteByUserId(Long userId); // 유저 번호에 맞는 게시글들 삭제
}
