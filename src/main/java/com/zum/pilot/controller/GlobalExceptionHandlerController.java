package com.zum.pilot.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandlerController {

  @ExceptionHandler(value = NoHandlerFoundException.class)
  public String handleNotFoundException(Exception e) {
    return "error/404";
  }

  @ExceptionHandler(value = NullPointerException.class)
  public String handleNullPointerException(Exception e) {
    return "error/errorNull";
  }
}
