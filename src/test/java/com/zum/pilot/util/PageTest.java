package com.zum.pilot.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PageTest {

  @Test
  public void 조회결과가_없을경우_페이지네이션() {
    int currentPage = 1;
    int totalElemCount = 0;
    List<Object> something = Collections.emptyList();

    Page<Object> page = PagenationUtil.getPage(currentPage, totalElemCount, something);

    assertThat(page.getBegin(), is(1));
    assertThat(page.getEnd(), is(1));
    assertThat(page.getTotalPageNum(), is(1));
    assertThat(page.getCurrentPageNum(), is(1));
    assertThat(page.getPageNumUnit(), is(5));
    assertThat(page.getElemList(), is(something));
  }

  @Test
  public void  조회결과가_한개일경우_페이지네이션() {
    int currentPage = 1;
    int totalElemCount = 1;
    List<Object> something = new ArrayList<>();
    something.add(new Object());

    Page<Object> page = PagenationUtil.getPage(currentPage, totalElemCount, something);

    assertThat(page.getBegin(), is(1));
    assertThat(page.getEnd(), is(1));
    assertThat(page.getTotalPageNum(), is(1));
    assertThat(page.getCurrentPageNum(), is(1));
    assertThat(page.getPageNumUnit(), is(5));
    assertThat(page.getElemList(), is(something));
  }

  @Test
  public void  조회결과가_열개일경우_페이지네이션() {
    int currentPage = 1;
    int totalElemCount = 10;
    List<Object> something = new ArrayList<>();
    something.add(new Object());

    Page<Object> page = PagenationUtil.getPage(currentPage, totalElemCount, something);

    assertThat(page.getBegin(), is(1));
    assertThat(page.getEnd(), is(1));
    assertThat(page.getTotalPageNum(), is(1));
    assertThat(page.getCurrentPageNum(), is(1));
    assertThat(page.getPageNumUnit(), is(5));
    assertThat(page.getElemList(), is(something));
  }

  @Test
  public void 페이지가_끝페이지일경우() {
    int currentPage = 1;
    int totalElemCount = 45;
    List<Object> something = new ArrayList<>();
    for(int i=0 ; i<totalElemCount ; i++)
      something.add(new Object());

    Page<Object> page = PagenationUtil.getPage(currentPage, totalElemCount, something);

    assertThat(page.getBegin(), is(1));
    assertThat(page.getEnd(), is(5));
    assertThat(page.getTotalPageNum(), is(5));
    assertThat(page.getCurrentPageNum(), is(1));
    assertThat(page.getPageNumUnit(), is(5));
    assertThat(page.getElemList(), is(something));
    assertThat(page.isEndPage(), is(true));
  }

  @Test
  public void 페이지가_끝페이지가_아닐경우() {
    int currentPage = 1;
    int totalElemCount = 200;
    List<Object> something = new ArrayList<>();
    for(int i=0 ; i<totalElemCount ; i++)
      something.add(new Object());

    Page<Object> page = PagenationUtil.getPage(currentPage, totalElemCount, something);

    assertThat(page.getBegin(), is(1));
    assertThat(page.getEnd(), is(5));
    assertThat(page.getCurrentPageNum(), is(1));
    assertThat(page.isEndPage(), is(false));
  }

}