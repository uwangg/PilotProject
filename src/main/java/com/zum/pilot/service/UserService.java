package com.zum.pilot.service;

import com.zum.pilot.entity.User;

public interface UserService {
  User checkEmailAndPassword(String email, String password); // 로그인
  void create(User user); // 회원 생성
  boolean checkEmail(String email); // 이메일 체크
  boolean checkName(String name);
  boolean checkPassword(Long id, String password);
  void update(User user);
  void delete(Long id, String password);
}
