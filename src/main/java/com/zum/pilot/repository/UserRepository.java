package com.zum.pilot.repository;

import com.zum.pilot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  UserEntity findByIdAndPasswordAndDeleteFlag(Long id, String password, boolean deleteFlag);
  UserEntity findByEmailAndPasswordAndDeleteFlag(String email, String password, boolean deleteFlag);
  UserEntity findByEmailAndDeleteFlag(String email, boolean deleteFlag);
  UserEntity findUserEntityByNameAndDeleteFlag(String name, boolean deleteFlag);
}
