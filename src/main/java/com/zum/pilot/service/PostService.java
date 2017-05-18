package com.zum.pilot.service;

import com.zum.pilot.dao.PostDao;
import com.zum.pilot.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

  @Autowired
  private PostDao postDao;

  // 전체 페이지 수를 구해줌
  public int totalNumberOfPage(int postUnit) {
    Long totalPostNum = 0L;
    int totalPageNum = 0;    // 총 페이지 번호의 수

    totalPostNum = postDao.totalNumberOfPost();
    totalPageNum = (int) ((totalPostNum - 1) / postUnit + 1);
    return totalPageNum;
  }

  // 눌린 페이지 번호와 가져올 글의 갯수로 게시글 목록 불러오기
  public List<PostVo> getList(int currentPageNum, int postUnit) {
    List<PostVo> list = postDao.getList(currentPageNum, postUnit);
    return list;
  }
}
