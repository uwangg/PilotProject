package com.zum.pilot.service;


import com.zum.pilot.dao.UserDao;
import com.zum.pilot.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
  @Autowired
  private UserDao userDao;

  @Autowired
  private PostService postService;

  @Autowired
  private CommentService commentService;

  public UserVo get(UserVo vo) {
    UserVo userVo = userDao.get(vo);
    return userVo;
  }

  public void insert(UserVo userVo) {
    userDao.insert(userVo);
  }

  public boolean checkEmail(String email) {
    return userDao.checkEmail(email);
  }

  public boolean checkName(String name) {
    return userDao.checkName(name);
  }

  public boolean checkPassword(Long id, String password) {
    return userDao.checkPassword(id, password);
  }

  public int update(UserVo vo, String newPassword) {
    return userDao.update(vo, newPassword);
  }

  @Transactional
  public void delete(Long id, String password) {
    userDao.delete(id, password);
    postService.deleteByUser(id);
    commentService.deleteByUser(id);
    throw new RuntimeException("강제 오류발생");
  }
}
