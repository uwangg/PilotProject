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
}
