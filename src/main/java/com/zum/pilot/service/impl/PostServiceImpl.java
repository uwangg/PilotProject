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
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
  @Autowired
  private PostRepository postRepository;

  @Autowired
  private CommentService commentService;

  @Resource(name = "uploadPath")
  String uploadPath;

  // 게시글 페이지네이션
  @Override
  public Page<PostEntity> findAllPostList(Pageable pageable) {
    Page<PostEntity> posts = postRepository.findAllByDeleteFlag(false, pageable);
    return posts;
  }

  @Override
  @Transactional
  public PostEntity readPost(Long id) {
    // 게시글 불러오기
    PostEntity postEntity = postRepository.findOne(id);
    postEntity.increaseHit();
    // 게시글 업데이트
    return postRepository.save(postEntity);
  }

  @Override
  public PostEntity getPost(Long id) {
    return postRepository.findByIdAndDeleteFlag(id, false);
  }

  @Override
  public void createPost(String title, String content, MultipartFile file, Long userId) {
    PostEntity postEntity = new PostEntity(title, content, userId);

    if(!file.isEmpty()) {
      String saveName = fileUpload(file);
      postEntity.setImagePath(saveName);
    }
    // 게시글 입력
    postRepository.save(postEntity);
  }

  private String fileUpload(MultipartFile file) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
    Date date = new Date();
    // 이미지 등록
    String saveName = sdf.format(date) + "_" + file.getOriginalFilename();
    File target = new File(uploadPath, saveName);
    // 임시 디렉토리에 저장된 업로드된 파일을 지정된 디렉토리로 복사
    try {
      FileCopyUtils.copy(file.getBytes(), target);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return saveName;
  }

  @Override
  @Transactional
  public void modifyPost(Long postId, String title, String content, MultipartFile file) {
    PostEntity postEntity = postRepository.findByIdAndDeleteFlag(postId, false);
    postEntity.setTitle(title);
    postEntity.setContent(content);
    String oldImgPath = postEntity.getImagePath();

    if(!file.isEmpty()) {
      String saveName = fileUpload(file);
      postEntity.setImagePath(saveName);
      if(oldImgPath != null) {
        // 원래 이미지 삭제
        File uploadFile = new File(uploadPath + "/" + oldImgPath);
        if (uploadFile.exists() && uploadFile.isFile())
          uploadFile.delete();
      }
    }
    postRepository.save(postEntity);
  }

  @Override
  @Transactional
  public void deletePost(Long postId, Long authId) {
    PostEntity postEntity = postRepository.findByIdAndDeleteFlag(postId, false);
    if (postEntity.getUserId() != authId) {
      return;
    }
    // 이미지가 있다면 삭제
    if (!"".equals(postEntity.getImagePath())) {
      File uploadFile = new File(uploadPath + "/" + postEntity.getImagePath());
      if (uploadFile.exists() && uploadFile.isFile())
        uploadFile.delete();
    }
    // 게시글 삭제
    postEntity.setDeleteFlag(true);
    postRepository.save(postEntity);
    commentService.deleteCommentByPostId(postId);
  }

  @Override
  @Transactional
  public void deleteByUserId(Long userId) {
    List<PostEntity> posts = postRepository.findAllByUserEntityIdAndDeleteFlag(userId, false);
    for(PostEntity postEntity : posts) {
      postEntity.setDeleteFlag(true);
      postRepository.save(postEntity);
    }
  }
}
