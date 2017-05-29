package com.zum.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.zum.pilot.dao")
public class PersistenceJPAConfig {
  @Autowired
  private Environment env;

  // Datasource Bean
  @Bean(destroyMethod = "close")
  public DataSource dataSource() throws Exception{
    BasicDataSource dataSource = new BasicDataSource();
//    dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//    dataSource.setUrl(env.getProperty("jdbc.url"));
//    dataSource.setUsername(env.getProperty("jdbc.username"));
//    dataSource.setPassword(env.getProperty("jdbc.password"));
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/pilot");
    dataSource.setUsername("pilot");
    dataSource.setPassword("pilot");
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//    em.setJpaDialect(new HibernateJpaDialect());
    em.setPackagesToScan("com.zum.pilot");
    em.setJpaProperties(hibernateProperties());
    return em;
//    Properties properties = hibernateProperties();

//    properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
//    properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//    properties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
//    properties.setProperty("hibernate.use_sql_comments", env.getProperty("hibernate.use_sql_comments"));
//    properties.setProperty("hibernate.id.new_generator_mapping", env.getProperty("hibernate.id.new_generator_mapping"));

//    em.setJpaPropertyMap(hibernateProperties());
  }

  private Properties hibernateProperties() {
    return new Properties() {
      {
        setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        setProperty("hibernate.use_sql_comments", env.getProperty("hibernate.use_sql_comments"));
        setProperty("hibernate.id.new_generator_mapping", env.getProperty("hibernate.id.new_generator_mapping"));
      }
    };
  }

  // Transaction 설정
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }
}
