package com.zum.pilot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

@Configuration
public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Filter[] getServletFilters() {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding("utf-8");
    characterEncodingFilter.setForceEncoding(true);
    return new Filter[] {characterEncodingFilter};
  }

  @Override
  protected WebApplicationContext createRootApplicationContext() {
    AnnotationConfigWebApplicationContext applicationContext =
            new AnnotationConfigWebApplicationContext();
    applicationContext.register(RootConfig.class);
    return applicationContext;
  }

  @Override
  protected WebApplicationContext createServletApplicationContext() {
    AnnotationConfigWebApplicationContext applicationContext =
            new AnnotationConfigWebApplicationContext();
    applicationContext.register(WebConfig.class);
    return applicationContext;
  }

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[]{RootConfig.class};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[]{WebConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[]{"/"};
  }
}
