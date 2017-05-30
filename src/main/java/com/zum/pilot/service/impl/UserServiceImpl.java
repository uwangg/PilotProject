package com.zum.pilot.service.impl;

import com.zum.pilot.dao.UserRepository;
import com.zum.pilot.service.UserService;
import com.zum.pilot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User findById(Long id) {
    return userRepository.findOne(id);
  }

  @Override
  public User checkEmailAndPassword(String email, String password) {
    return userRepository.findByEmailAndPasswordAndDeleteFlag(email, password, false);
  }

  @Override
  public void create(User user) {
    userRepository.save(user);
  }

  @Override
  public boolean checkEmail(String email) {
    User user = userRepository.findByEmailAndDeleteFlag(email, false);
    if(user == null)
      return false;
    else
      return true;
  }

  @Override
  public boolean checkName(String name) {
    User user = userRepository.findUserByNameAndDeleteFlag(name, false);
    if(user == null)
      return false;
    else
      return true;
  }

  @Override
  public boolean checkPassword(Long id, String password) {
    User user = userRepository.findByIdAndPasswordAndDeleteFlag(id, password, false);
    if(user == null)
      return false;
    else
      return true;
  }

  @Override
  public void update(User user) {
    userRepository.save(user);
  }

  @Override
  public void delete(Long id, String password) {
    User user = userRepository.findByIdAndPasswordAndDeleteFlag(id, password, false);
    if(user != null) {
      user.setDeleteFlag(true);
      userRepository.save(user);
    }
  }
}
