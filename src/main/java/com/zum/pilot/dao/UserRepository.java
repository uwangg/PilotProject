package com.zum.pilot.dao;

import com.zum.pilot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//  @Query("select u from user u where u.id=:id and u.password=:password and u.delete_flag=0")
//  User findByUser(@Param("id") Long id, @Param("password") String password); // 로그인
  User findByIdAndPasswordAndDeleteFlag(Long id, String password, boolean deleteFlag);

  User findByEmailAndPasswordAndDeleteFlag(String email, String password, boolean deleteFlag);

//  @Query("select u from user u where u.email=:email and u.delete_flag=0")
//  boolean checkEmail(@Param("email") String email); // 이메일 체크
  User findByEmailAndDeleteFlag(String email, boolean deleteFlag);

//  @Query("select u from User u where u.name=:name and u.delete_flag=0")
//  boolean checkName(@Param("name") String name);
  User findUserByNameAndDeleteFlag(String name, boolean deleteFlag);
}