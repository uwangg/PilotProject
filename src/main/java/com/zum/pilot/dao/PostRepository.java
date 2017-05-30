package com.zum.pilot.dao;


import com.zum.pilot.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  Page<Post> findAllByDeleteFlag(boolean deleteFlag, Pageable pageable);
  Post findByIdAndDeleteFlag(Long id, boolean deleteFlag);
//  List<Post> findAllByUser_UserIdIdAndDeleteFlag(Long id, boolean deleteFlag);
  @Query("select p from Post p where p.user.id=:userId and p.deleteFlag=0")
  List<Post> findAllByUserIdAndDeleteFlag(@Param("userId") Long userId);
}
