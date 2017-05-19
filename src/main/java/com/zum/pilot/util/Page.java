package com.zum.pilot.util;

import java.util.List;

public class Page<E> {
  private final int ELEMENT_UNIT = 10;

  private int begin = 1;
  private int end  = 1;
  private boolean isEndPage = false;
  private int totalPageNum;
  private int currentPageNum;
  private int pageNumUnit = 5;
  private List<E> elemList;
  public Page(int current, long totalElemCount, List<E> elemList) {
    this.currentPageNum = current;
    this.totalPageNum = getTotalCountOfPage(totalElemCount);
    this.elemList = elemList;
    calcuratePage(current);
    checkEndPage();
  }

  private void checkEndPage() {
    isEndPage = (end == totalPageNum);
  }

  private void calcuratePage(int current) {
    begin = (current-1)/pageNumUnit + 1;
    end = (begin-1) + pageNumUnit;
    if(end >= totalPageNum) {
      end = totalPageNum;
    }
  }

  private int getTotalCountOfPage(long totalElemCount) {
    return (int) ((totalElemCount - 1) / ELEMENT_UNIT + 1);
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
    return currentPageNum;
  }

  public int getPageNumUnit() {
    return pageNumUnit;
  }

  public List<E> getElemList() {
    return elemList;
  }

  public boolean isEndPage() {
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
