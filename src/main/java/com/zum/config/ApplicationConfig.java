package com.zum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value = {"classpath:applicationContext.xml"})
public class ApplicationConfig {
}
