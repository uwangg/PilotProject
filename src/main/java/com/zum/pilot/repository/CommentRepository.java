package com.zum.pilot.repository;

import com.zum.pilot.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
  CommentEntity findByIdAndDeleteFlag(Long commentId, boolean deleteFlag);

  Page<CommentEntity> findAllByPostEntityId(Long postId, Pageable pageable);

  @Query("select max(c.thread) from CommentEntity c where c.postEntity.id=:postId")
  Integer getMaxThread(@Param("postId") Long postId);

  List<CommentEntity> findAllByThreadGreaterThanAndThreadLessThan(int begin, int end);

  @Query("select c from CommentEntity c where c.userEntity.id=:userId and c.deleteFlag=0")
  List<CommentEntity> findAllByUserEntityIdAndDeleteFlag(@Param("userId") Long userId);
  @Query("select c from CommentEntity c where c.postEntity.id=:postId and c.deleteFlag=0")
  List<CommentEntity> findAllByPostEntityIdAndDeleteFlag(Long postId);
}
