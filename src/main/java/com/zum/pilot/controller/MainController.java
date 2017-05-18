package com.zum.pilot.controller;


import com.zum.pilot.dao.PostDao;
import com.zum.pilot.service.PostService;
import com.zum.pilot.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

  @Autowired
  private PostService postService;

  @RequestMapping("")
  public String main(
          @RequestParam(value = "currentPageNum", defaultValue = "1") int currentPageNum, // 선택된 페이지 번호
          @RequestParam(value = "begin", defaultValue = "1") int begin,
          @RequestParam(value = "end", defaultValue = "1") int end,
          Model model) {

    // 게시글 페이지네이션
    int totalPageNum = 0;    // 총 페이지 번호의 수
    int postUnit = 10;    // 한 페이지당 보여줄 글의 최대 갯수
    int pageNumUnit = 5;    // 한 페이지 블락당 보여줄 번호의 최대 갯수

    List<PostVo> postList = null;

    totalPageNum = postService.totalNumberOfPage(postUnit);
    postList = postService.getList((totalPageNum - currentPageNum) * postUnit, postUnit); // 선택된 페이지번호, 보여줄 글의갯수

    end = (begin - 1) + pageNumUnit;
    if (end > totalPageNum)
      end = totalPageNum;

    model.addAttribute("begin", begin);
    model.addAttribute("end", end);
    model.addAttribute("totalPageNum", totalPageNum);
    model.addAttribute("currentPageNum", currentPageNum);
    model.addAttribute("pageNumUnit", pageNumUnit);
    model.addAttribute("postList", postList);

    return "forward:/WEB-INF/views/main/index.jsp";
  }
}