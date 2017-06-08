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
  public void createUser(UserEntity userEntity) {
    userRepository.save(userEntity);
  }

  @Override
  public boolean checkEmail(String email) {
    UserEntity userEntity = userRepository.findByEmailAndDeleteFlag(email, false);
    if(userEntity == null)
      return true;
    else
      return false;
  }

  @Override
  public boolean checkName(String name, UserEntity authUser) {
    // 회원 수정시 닉네임 중복체크
    if (authUser != null) {
      if (authUser.getName().equals(name)) {
        return true;
      }
    }
    UserEntity userEntity = userRepository.findUserEntityByNameAndDeleteFlag(name, false);
    if(userEntity == null)  // 중복된 유저가없으므로 true
      return true;
    else
      return false;
  }

  @Override
  public UserEntity modifyUser(Long id, String name, String password, String changePassword) {
    UserEntity userEntity = userRepository.findByIdAndPasswordAndDeleteFlag(id, password, false);
    if(userEntity == null) {
      return null;
    }
    userEntity.setName(name);
    if(!"".equals(changePassword)) {
      userEntity.setPassword(changePassword);
    }
    userRepository.save(userEntity);
    return userEntity;
  }

  @Override
  @Transactional
  public boolean deleteUser(Long id, String password) {
    UserEntity userEntity = userRepository.findByIdAndPasswordAndDeleteFlag(id, password, false);
    if(userEntity == null) {
      return false;
    }
    userEntity.setDeleteFlag(true);
    userRepository.save(userEntity);
    postService.deleteByUserId(id);
    commentService.deleteCommentByUserId(id);
    return true;
  }
}
