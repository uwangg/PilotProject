package com.zum.pilot.repository;

import com.zum.pilot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByIdAndPasswordAndDeleteFlag(Long id, String password, boolean deleteFlag);
  User findByEmailAndPasswordAndDeleteFlag(String email, String password, boolean deleteFlag);
  User findByEmailAndDeleteFlag(String email, boolean deleteFlag);
  User findUserByNameAndDeleteFlag(String name, boolean deleteFlag);
}
