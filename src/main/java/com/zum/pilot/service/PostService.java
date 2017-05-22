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

  // 게시글 불러오기
  public Pagination<PostVo> viewPage(int currentPage) {
    Long totalPostNum = postDao.totalNumberOfPost();  // 총 게시글의 갯수 가져옴
    int beginPostNum = (currentPage - 1) * PageConstant.ELEMENT_UNIT; // 현재 페이지의 시작 게시글 인덱스
    List<PostVo> list = postDao.getList(beginPostNum, PageConstant.ELEMENT_UNIT); // 눌린 페이지 번호의 범위안의 게시글 가져오기
    Pagination<PostVo> pagination = new Pagination<>(currentPage, totalPostNum, list);
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
