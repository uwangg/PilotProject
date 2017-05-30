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
  Page<Comment> findAllByPostId(Long postId, Pageable pageable);

  @Query("select max(c.thread) from Comment c where c.post.id=:postId")
  Integer getMaxThread(@Param("postId") Long postId);

  List<Comment> findAllByThreadLessThanAndThreadGreaterThan(int begin, int end);
}
