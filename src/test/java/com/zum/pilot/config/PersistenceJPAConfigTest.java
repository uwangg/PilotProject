package com.zum.pilot.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
public class PersistenceJPAConfigTest {

//  @Autowired
//  PersistenceJPAConfig persistenceJPAConfig;
  @Autowired
  private BasicDataSource basicDataSource;
  @Test
  public void 디비연결테스트() {
    System.out.println(basicDataSource);
  }
}