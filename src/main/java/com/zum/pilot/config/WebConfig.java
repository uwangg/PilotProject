package com.zum.pilot.config;

import com.zum.pilot.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc // <mvc:annotation-driven/>
@ComponentScan(basePackages = {"com.zum.pilot"}) //  <context:component-scan base-package="com.zum.pilot" />
public class WebConfig extends WebMvcConfigurerAdapter{

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new AuthInterceptor())
            .addPathPatterns("/user/*", "/board/**")
            .excludePathPatterns("/board/*", "/user/login", "/user/join", "/user/checkemail", "/user/checkname", "/user/joinsuccess");
  }

  @Bean
  public InternalResourceViewResolver internalResourceViewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".jsp");
    return viewResolver;
  }

  // MultipartResolver
  @Bean
  public CommonsMultipartResolver multipartResolver() {
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    multipartResolver.setMaxUploadSize(10485760); // 파일 업로드 용량
    multipartResolver.setDefaultEncoding("utf-8");
    return multipartResolver;
  }

  // 파일 업로드 경로
  @Bean
  public String uploadPath() {
    return "D:\\test\\upload";
  }
}
