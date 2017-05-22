package com.zum.pilot.controller;


import com.zum.pilot.service.PostService;
import com.zum.pilot.util.Pagination;
import com.zum.pilot.vo.PostVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainController {

  @Autowired
  private PostService postService;

  @RequestMapping("")
  public String main(
          @RequestParam(value = "currentPage", defaultValue = "1") int currentPage, // 선택된 페이지 번호
          Model model) {

    // 게시글 불러오기
    Pagination<PostVo> pagination = postService.viewPage(currentPage);

    model.addAttribute("pagination", pagination);
    return "forward:/WEB-INF/views/main/index.jsp";
  }
}