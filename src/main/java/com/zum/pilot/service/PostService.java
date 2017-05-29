package com.zum.pilot.service;

import com.zum.pilot.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
//  Pagination<PostVo> viewPage(int currentPage);
  Page<Post> findAllPostList(Pageable pageable);
}
