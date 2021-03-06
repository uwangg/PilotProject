package com.zum.pilot.repository;


import com.zum.pilot.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
  Page<PostEntity> findAllByDeleteFlag(boolean deleteFlag, Pageable pageable);
  PostEntity findByIdAndDeleteFlag(Long id, boolean deleteFlag);
  List<PostEntity> findAllByUserEntityIdAndDeleteFlag(Long userId, boolean deleteFlag);
}
