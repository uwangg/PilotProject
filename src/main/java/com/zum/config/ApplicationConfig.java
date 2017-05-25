package com.zum.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

// applicationContext.xml 설정을 대신한다
@Configuration
//@Import(value = {PersistenceJPAConfig.class})
@ComponentScan(basePackages = {"com.zum.pilot"})
public class ApplicationConfig {
  // jdbc에서 jpa로 변경 후 transaction 처리 할 예정
  // 아직 구현하지 않음
}
