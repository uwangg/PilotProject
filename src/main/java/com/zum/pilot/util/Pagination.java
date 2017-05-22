package com.zum.pilot.util;

import java.util.List;

public class Pagination<E> {
  private int begin = 1;
  private int end  = 1;
  private boolean isEndPage = false;
  private int totalPageNum;
  private int currentPage;
  private List<E> elemList;

  public Pagination(int currentPage, long totalElemCount, List<E> elemList) {
    this.currentPage = currentPage;
    this.totalPageNum = getTotalCountOfPage(totalElemCount);
    this.elemList = elemList;
    calcuratePage(currentPage);
    checkEndPage();
  }

  private void checkEndPage() {
    isEndPage = (end == totalPageNum);
  }

  private void calcuratePage(int currentPage) {
    begin = (currentPage-1)/PageConstant.PAGE_UNIT * PageConstant.PAGE_UNIT + 1;
    end = (begin-1) + PageConstant.PAGE_UNIT;
    if(end >= totalPageNum) {
      end = totalPageNum;
    }
  }

  private int getTotalCountOfPage(long totalElemCount) {
    return (int) ((totalElemCount - 1) / PageConstant.ELEMENT_UNIT + 1);
  }

  public int getBegin() {
    return begin;
  }

  public int getEnd() {
    return end;
  }

  public int getTotalPageNum() {
    return totalPageNum;
  }

  public int getCurrentPageNum() {
    return currentPage;
  }

  public List<E> getElemList() {
    return elemList;
  }

  public boolean getIsEndPage() {
    return isEndPage;
  }
}

/**
 *
 model.addAttribute("begin", begin);
 model.addAttribute("end", end);
 model.addAttribute("totalPageNum", totalPageNum);
 model.addAttribute("currentPageNum", currentPageNum);
 //model.addAttribute("pageNumUnit", pageNumUnit);
 model.addAttribute("postList", postList);
 */
