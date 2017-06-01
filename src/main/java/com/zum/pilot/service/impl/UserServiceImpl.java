package com.zum.pilot.service.impl;

import com.zum.pilot.repository.UserRepository;
import com.zum.pilot.service.CommentService;
import com.zum.pilot.service.PostService;
import com.zum.pilot.service.UserService;
import com.zum.pilot.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PostService postService;

  @Autowired
  private CommentService commentService;

  @Override
  public UserEntity checkEmailAndPassword(String email, String password) {
    return userRepository.findByEmailAndPasswordAndDeleteFlag(email, password, false);
  }

  @Override
  public void create(UserEntity userEntity) {
    userRepository.save(userEntity);
  }

  @Override
  public boolean checkEmail(String email) {
    UserEntity userEntity = userRepository.findByEmailAndDeleteFlag(email, false);
    if(userEntity == null)
      return false;
    else
      return true;
  }

  @Override
  public boolean checkName(String name) {
    UserEntity userEntity = userRepository.findUserEntityByNameAndDeleteFlag(name, false);
    if(userEntity == null)
      return false;
    else
      return true;
  }

  @Override
  public boolean checkPassword(Long id, String password) {
    UserEntity userEntity = userRepository.findByIdAndPasswordAndDeleteFlag(id, password, false);
    if(userEntity == null)
      return false;
    else
      return true;
  }

  @Override
  public void update(UserEntity userEntity) {
    userRepository.save(userEntity);
  }

  @Override
  @Transactional
  public void delete(Long id, String password) {
    UserEntity userEntity = userRepository.findByIdAndPasswordAndDeleteFlag(id, password, false);
    if(userEntity != null) {
      userEntity.setDeleteFlag(true);
      userRepository.save(userEntity);
      postService.deleteByUserId(id);
      commentService.deleteCommentByUserId(id);
    }
  }
}
