//package com.zum.pilot.dao;
//
//import com.zum.pilot.entity.Post;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import java.util.List;
//
//@Repository
//public class PostRepositoryCustomImpl implements PostRepositoryCustom {
//
//  @PersistenceContext
//  private EntityManager em;
//
//  @Override
//  @Transactional
//  public List<Post> findAllByUserIdAndDeleteFlag(Long userId, boolean deleteFlag) {
//    final String queryString = "select p from Post p where p.user.id=:userId and p.deleteFlag=0";
//    TypedQuery<Post> query = em.createQuery(queryString, Post.class);
//    query.setParameter("userId", userId);
////    query.setParameter("deleteFlag", false);
//    List<Post> posts = query.getResultList();
//    return posts;
//  }
//}
