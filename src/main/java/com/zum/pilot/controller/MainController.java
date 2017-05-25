package com.zum.pilot.controller;


import com.zum.pilot.service.PostService;
import com.zum.pilot.service.PostService2;
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

  private final static Logger logger =
          LoggerFactory.getLogger(MainController.class);

  @Autowired
  private PostService postService;

  @Autowired
  PostService2 postService2;

  @RequestMapping("")
  public String main(
          @RequestParam(value = "currentPage", defaultValue = "1") int currentPage, // 선택된 페이지 번호
          Model model) {

    // 게시글 불러오기
    Pagination<PostVo> pagination = postService.viewPage(currentPage);

    PostVo postVo = postService2.findPostById(2L);
    logger.info("jpa test = " + postVo.getTitle());
    model.addAttribute("pagination", pagination);
    return "forward:/WEB-INF/views/main/index.jsp";
  }
}