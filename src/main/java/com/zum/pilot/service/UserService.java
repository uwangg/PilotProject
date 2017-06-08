package com.zum.pilot.service;

import com.zum.pilot.entity.UserEntity;

public interface UserService {
  UserEntity checkEmailAndPassword(String email, String password); // 로그인
  void create(UserEntity userEntity); // 회원 생성
  boolean checkEmail(String email); // 이메일 체크
  boolean checkName(String name, UserEntity authUser);
  boolean checkPassword(Long id, String password);
  UserEntity modifyUser(Long id, String name, String password, String changePassword);
  boolean deleteUser(Long id, String password);
}
