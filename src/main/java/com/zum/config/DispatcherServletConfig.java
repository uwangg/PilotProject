package com.zum.config;

import com.zum.pilot.interceptor.AuthLoginInterceptor;
import com.zum.pilot.interceptor.AuthLogoutInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc // <mvc:annotation-driven/>
@ComponentScan(basePackages = {"com.zum.pilot"}) //  <context:component-scan base-package="com.zum.pilot" />
public class DispatcherServletConfig extends WebMvcConfigurerAdapter{

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new AuthLoginInterceptor()).addPathPatterns("/user/login");
    registry.addInterceptor(new AuthLogoutInterceptor()).addPathPatterns("/user/logout");
  }

  @Bean
  public InternalResourceViewResolver internalResourceViewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }
}
