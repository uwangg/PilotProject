package com.zum.pilot.service;


import com.zum.pilot.dao.UserDao;
import com.zum.pilot.entity.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService2 {
  @Autowired
  private UserDao userDao;

  @Autowired
  private PostService2 postService2;

  @Autowired
  private CommentService2 commentService2;

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
    postService2.deleteByUser(id);
    commentService2.deleteByUser(id);
    throw new RuntimeException("강제 오류발생");
  }
}
