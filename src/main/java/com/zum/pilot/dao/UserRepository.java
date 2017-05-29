package com.zum.pilot.dao;

import com.zum.pilot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//  @Query("select u from user u where u.id=:id and u.password=:password and u.delete_flag=0")
//  User findByUser(@Param("id") Long id, @Param("password") String password); // 로그인
  User findByIdAndPassword(Long id, String password);

//  @Query("select u from user u where u.email=:email and u.delete_flag=0")
//  boolean checkEmail(@Param("email") String email); // 이메일 체크
  User findByEmail(String email);

//  @Query("select u from User u where u.name=:name and u.delete_flag=0")
//  boolean checkName(@Param("name") String name);
  User findByName(String name);
}
