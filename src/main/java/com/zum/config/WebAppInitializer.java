package com.zum.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;

// web.xml 대신할 WebAppInitializer
public class WebAppInitializer implements WebApplicationInitializer {
  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {

    // ApplicationConfig 설정
    AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
    applicationContext.setDisplayName("pilot-project");
    applicationContext.register(ApplicationConfig.class);
    servletContext.addListener(new ContextLoaderListener(applicationContext));

    // DispatcherServletConfig 설정
    AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
    dispatcherServlet.register(DispatcherServletConfig.class);
    ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/");

    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding("utf-8");
    characterEncodingFilter.setForceEncoding(true);

    servletContext.addFilter("encodingFilter", characterEncodingFilter).addMappingForUrlPatterns(null, false, "/*");
    // Filter
//    FilterRegistration.Dynamic boardLoginFilter = servletContext.addFilter("boardLoginFilter", new BoardLoginFilter());
//    boardLoginFilter.addMappingForUrlPatterns(null, true, "/board");
//    FilterRegistration.Dynamic userLoginFilter = servletContext.addFilter("userLoginFilter", new UserLoginFilter());
//    userLoginFilter.addMappingForUrlPatterns(null, true, "/user");
//    servletContext.addFilter("BoardLonginFilter",
//            BoardLoginFilter.class).addMappingForUrlPatterns(null, true, "/board");
//    servletContext.addFilter("UserLoginFilter",
//            UserLoginFilter.class).addMappingForUrlPatterns(null, true, "/user");

  }
}
