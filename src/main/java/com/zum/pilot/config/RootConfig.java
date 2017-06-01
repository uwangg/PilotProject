package com.zum.pilot.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.*;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

// applicationContext.xml 설정을 대신한다
@Configuration
//@Import(value = {PersistenceJPAConfig.class})
@ComponentScan(basePackages = {"com.zum.pilot"})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.zum.pilot.repository")
public class RootConfig {
  // jdbc에서 jpa로 변경 후 transaction 처리 할 예정
  // 아직 구현하지 않음
  @Bean
  public DataSource dataSource() {
    DataSource dataSource = new DataSource();
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/pilot?useSSL=false");
    dataSource.setUsername("pilot");
    dataSource.setPassword("pilot");
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    entityManagerFactoryBean.setPackagesToScan("com.zum.pilot.entity");
    entityManagerFactoryBean.setJpaProperties(additionalProperties());
    return entityManagerFactoryBean;
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(emf);
    return transactionManager;
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
    return new PersistenceExceptionTranslationPostProcessor();
  }


  Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
    properties.setProperty("hibernate.hbm2ddl.auto", "validate");
//    properties.setProperty("hibernate.hbm2ddl.auto", "create");
    properties.setProperty("hibernate.show_sql", "true");
    properties.setProperty("hibernate.format_sql", "true");
    properties.setProperty("hibernate.use_sql_comments", "true");
    properties.setProperty("hibernate.id.new_generator_mappings", "true");
    properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");

    return properties;
  }
}
