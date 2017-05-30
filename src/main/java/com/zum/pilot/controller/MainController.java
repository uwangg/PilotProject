package com.zum.pilot.controller;


import com.zum.pilot.entity.Post;
import com.zum.pilot.service.PostService;
import com.zum.pilot.util.PageConstant;
import com.zum.pilot.util.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

  private final static Logger logger =
          LoggerFactory.getLogger(MainController.class);


  @Autowired
  private PostService postService;

  @RequestMapping("")
  public String main(
          @RequestParam(value = "currentPage", defaultValue = "1") int currentPage, // 선택된 페이지 번호
          Model model) {

    // 게시글 불러오기

    PageRequest pageRequest = new PageRequest(currentPage-1, PageConstant.ELEMENT_UNIT, Sort.Direction.DESC, "id");
    Page<Post> page = postService.findAllPostList(pageRequest);
    List<Post> posts = page.getContent();
    Long totalPosts = page.getTotalElements();
    Pagination<Post> pagination = new Pagination<>(currentPage, totalPosts, posts);

    model.addAttribute("pagination", pagination);
    return "forward:/WEB-INF/views/main/index.jsp";
  }

  @ResponseBody
  @RequestMapping("/test")
  public Page<Post> main2(
          @RequestParam(value = "currentPage", defaultValue = "1") int currentPage) {

    // 게시글 불러오기
//    Pagination<PostVo> pagination = postService2.viewPage(currentPage);

    PageRequest pageRequest = new PageRequest(currentPage, 10, Sort.Direction.DESC, "id");
    Page<Post> pagination = postService.findAllPostList(pageRequest);
    return pagination;
  }
}