package com.zum.pilot.dao;

import com.zum.pilot.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  Comment findByIdAndDeleteFlag(Long commentId, boolean deleteFlag);

  Page<Comment> findAllByPostId(Long postId, Pageable pageable);

  @Query("select max(c.thread) from Comment c where c.post.id=:postId")
  Integer getMaxThread(@Param("postId") Long postId);

  List<Comment> findAllByThreadLessThanAndThreadGreaterThan(int begin, int end);

  @Query("select c from Comment c where c.user.id=:userId and c.deleteFlag=0")
  List<Comment> findAllByUserIdAndDeleteFlag(@Param("userId") Long userId);
  @Query("select c from Comment c where c.post.id=:postId and c.deleteFlag=0")
  List<Comment> findAllByPostIdAndDeleteFlag(Long postId);
}