package com.zum.pilot.service;

import com.zum.pilot.dao.CommentDao;
import com.zum.pilot.util.PageConstant;
import com.zum.pilot.util.Pagination;
import com.zum.pilot.entity.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService2 {
  @Autowired
  private CommentDao commentDao;

  public int getMaxThread(Long postId) {
    return commentDao.getMaxThread(postId);
  }

  public void updateThread(int begin, int end) {
    commentDao.updateThread(begin, end);
  }

  public void insert(CommentVo vo) {
    commentDao.insert(vo);
  }

  public void update(CommentVo vo) {
    commentDao.update(vo);
  }

  public void delete(Long commentId, Long userId) {
    commentDao.delete(commentId, userId);
  }
  public void deleteByUser(Long userId) {
    commentDao.deleteByUser(userId);
  }
  public void deleteByPost(Long postId) {
    commentDao.deleteByPost(postId);
  }

//  public int totalNumberOfPage(Long postId, int commentUnit) {
//    Long totalCommentNum = 0L;   // 게시글의 총 갯수
//    int totalPageNum = 0;    // 총 페이지 번호의 수
//
//    totalCommentNum = commentDao.totalNumberOfComment(postId);
//    totalPageNum = (int) ((totalCommentNum - 1) / commentUnit + 1);
//    return totalPageNum;
//  }
//
//  public List<CommentVo> getList(Long postId, int currentPageNum, int commentUnit) {
//    List<CommentVo> list = commentDao.getList(postId, currentPageNum, commentUnit);
//    return list;
//  }
  public Pagination<CommentVo> viewComment(int currentPage, Long postId) {
    Long totalCommentNum = commentDao.totalNumberOfComment(postId);
    int beginPostNum = (currentPage - 1) * PageConstant.ELEMENT_UNIT; // 현재 페이지의 시작 게시글 인덱스
    List<CommentVo> list = commentDao.getList(postId, beginPostNum, PageConstant.ELEMENT_UNIT); // 눌린 페이지 번호의 범위안의 게시글 가져오기
    Pagination<CommentVo> pagination = new Pagination<>(currentPage, totalCommentNum, list);
    return pagination;
  }
}
