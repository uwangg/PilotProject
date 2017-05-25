package com.zum.pilot.dao;

import com.zum.pilot.vo.PostVo;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostVo, Long> {
}
