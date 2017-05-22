package com.zum.pilot.service;

import com.zum.pilot.dao.PostDao;
import com.zum.pilot.util.Pagination;
import com.zum.pilot.util.PageConstant;
import com.zum.pilot.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PostService {

  @Autowired
  private PostDao postDao;

  // 전체 페이지 수를 구해줌
//  public int totalNumberOfPage(int postUnit) {
//    Long totalPostNum = 0L;
//    int totalPageNum = 0;    // 총 페이지 번호의 수
//
//    totalPostNum = postDao.totalNumberOfPost();
//    totalPageNum = (int) ((totalPostNum - 1) / postUnit + 1);
//    return totalPageNum;
//  }

  // 눌린 페이지 번호와 가져올 글의 갯수로 게시글 목록 불러오기
//  public List<PostVo> getList(int currentPageNum, int postUnit) {
//
//    Long totalPostNum = postDao.totalNumberOfPost();
//    int totalPageNum = 1;
//    currentPageNum = (totalPageNum - currentPageNum) * postUnit;
//    List<PostVo> list = postDao.getList(currentPageNum, postUnit);
//    return list;
//  }
  public Pagination<PostVo> viewPage(int currentPage) {
    Long totalPostNum = postDao.totalNumberOfPost();
    int beginPostNum = (currentPage - 1) * PageConstant.ELEMENT_UNIT; // 현재 페이지의 시작 게시글 인덱스
    List<PostVo> list = postDao.getList(beginPostNum, PageConstant.ELEMENT_UNIT);
    Pagination<PostVo> pagination = new Pagination<>(currentPage, totalPostNum, list);
//    int totalPageNum = 1;
//    currentPageNum = (totalPageNum - currentPageNum) * postUnit;
//    Pagination<PostVo> pagination = new Pagination<>(currentPageNum, totalPostNum);
//    list = postDao.getList(currentPageNum, postUnit);
    return pagination;
  }

  // 게시글 읽기
  public PostVo get(Long number) {
    postDao.hitIncrease(number);
    PostVo postVo = postDao.get(number);
    return postVo;
  }

  public void insert(PostVo vo) {
    postDao.insert(vo);
  }

  public void update(PostVo vo) {
    postDao.update(vo);
  }

  public void delete(Long postId, Long userId) {
    try {
      postDao.delete(postId, userId);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
