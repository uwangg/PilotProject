package com.zum.pilot.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PaginationTest {

  @Test
  public void 조회결과가_없을경우_페이지네이션() {
    int currentPage = 1;
    int totalElemCount = 0;
    List<Object> something = Collections.emptyList();

    Pagination<Object> pagination = new Pagination<>(currentPage, totalElemCount, something);

    assertThat(pagination.getBegin(), is(1));
    assertThat(pagination.getEnd(), is(1));
    assertThat(pagination.getTotalPageNum(), is(1));
    assertThat(pagination.getCurrentPage(), is(1));
    assertThat(pagination.getElemList(), is(something));
  }

  @Test
  public void  조회결과가_한개일경우_페이지네이션() {
    int currentPage = 1;
    int totalElemCount = 1;
    List<Object> something = new ArrayList<>();
    something.add(new Object());

    Pagination<Object> pagination = new Pagination<>(currentPage, totalElemCount, something);

    assertThat(pagination.getBegin(), is(1));
    assertThat(pagination.getEnd(), is(1));
    assertThat(pagination.getTotalPageNum(), is(1));
    assertThat(pagination.getCurrentPage(), is(1));
    assertThat(pagination.getElemList(), is(something));
  }

  @Test
  public void  조회결과가_열개일경우_페이지네이션() {
    int currentPage = 1;
    int totalElemCount = 10;
    List<Object> something = new ArrayList<>();
    something.add(new Object());

    Pagination<Object> pagination = new Pagination<>(currentPage, totalElemCount, something);

    assertThat(pagination.getBegin(), is(1));
    assertThat(pagination.getEnd(), is(1));
    assertThat(pagination.getTotalPageNum(), is(1));
    assertThat(pagination.getCurrentPage(), is(1));
    assertThat(pagination.getElemList(), is(something));
  }

  @Test
  public void 페이지가_끝페이지일경우() {
    int currentPage = 1;
    int totalElemCount = 45;
    List<Object> something = new ArrayList<>();
    for(int i=0 ; i<totalElemCount ; i++)
      something.add(new Object());

    Pagination<Object> pagination = new Pagination<>(currentPage, totalElemCount, something);

    assertThat(pagination.getBegin(), is(1));
    assertThat(pagination.getEnd(), is(5));
    assertThat(pagination.getTotalPageNum(), is(5));
    assertThat(pagination.getCurrentPage(), is(1));
    assertThat(pagination.getElemList(), is(something));
    assertThat(pagination.getIsEndPage(), is(true));
  }

  @Test
  public void 페이지가_끝페이지가_아닐경우() {
    int currentPage = 1;
    int totalElemCount = 200;
    List<Object> something = new ArrayList<>();
    for(int i=0 ; i<totalElemCount ; i++)
      something.add(new Object());

    Pagination<Object> pagination = new Pagination<>(currentPage, totalElemCount, something);

    assertThat(pagination.getBegin(), is(1));
    assertThat(pagination.getEnd(), is(5));
    assertThat(pagination.getCurrentPage(), is(1));
    assertThat(pagination.getIsEndPage(), is(false));
  }

}