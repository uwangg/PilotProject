package com.zum.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class PersistenceJPAConfig {
  @Autowired
  private Environment env;

  @Bean(destroyMethod = "close")
  public DataSource dataSource() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName(env.getProperty("database.driverClassName"));
    dataSource.setUrl(env.getProperty("database.url"));
    dataSource.setUsername(env.getProperty("database.username"));
    dataSource.setPassword(env.getProperty("database.password"));
    return dataSource;
  }
}
