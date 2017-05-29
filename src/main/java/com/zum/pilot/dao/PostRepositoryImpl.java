package com.zum.pilot.dao;

import com.zum.pilot.entity.Post;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {

  @PersistenceContext
  private EntityManager em;

  @Override
  @Transactional
  public Post getTotalPosts() {
    return em.find(Post.class, 1L);
  }
}
