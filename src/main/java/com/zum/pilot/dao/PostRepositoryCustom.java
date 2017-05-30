package com.zum.pilot.dao;

import com.zum.pilot.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {
  List<Post> findAllByUserIdAndDeleteFlag(Long userId, boolean deleteFlag);
}
