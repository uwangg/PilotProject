package com.zum.pilot.service;

import com.zum.pilot.dao.CommentDao;
import com.zum.pilot.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
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

  public int totalNumberOfPage(Long postId, int commentUnit) {
    Long totalCommentNum = 0L;   // 게시글의 총 갯수
    int totalPageNum = 0;    // 총 페이지 번호의 수

    totalCommentNum = commentDao.totalNumberOfComment(postId);
    totalPageNum = (int) ((totalCommentNum - 1) / commentUnit + 1);
    return totalPageNum;
  }

  public List<CommentVo> getList(Long postId, int currentPageNum, int commentUnit) {
    List<CommentVo> list = commentDao.getList(postId, currentPageNum, commentUnit);
    return list;
  }
}
