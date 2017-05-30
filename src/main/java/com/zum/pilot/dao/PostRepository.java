package com.zum.pilot.dao;


import com.zum.pilot.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
  Page<Post> findAllByDeleteFlag(boolean deleteFlag, Pageable pageable);
  Post findByIdAndDeleteFlag(Long id, boolean deleteFlag);
}
