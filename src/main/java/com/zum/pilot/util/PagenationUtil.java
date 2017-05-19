package com.zum.pilot.util;

import java.util.List;

public class PagenationUtil {
  public static int elementUnit = 10;
  public static int pageUnit = 5;

  public static int totalNumberOfPage(Long totalPostNum) {
    int totalPageNum = 0;    // 총 페이지 번호의 수
    totalPageNum = (int) ((totalPostNum - 1) / elementUnit + 1);
    return totalPageNum;
  }/*
  public static Page currentPage(int currentPageNum) {
    Page page = new Page(currentPageNum);
    page.begin = (currentPageNum - 1) / pageUnit * pageUnit;
    page.end = (page.begin -1) + pageUnit;
    *//*if(page.end >= )
      page.end = page.totalPageNum;*//*
    return page;
  }*/

  public static <E> Page<E> getPage(int currentPageNo, long totalElemCount, List<E> something) {
    return new Page<E>(currentPageNo, totalElemCount, something);
  }
}
