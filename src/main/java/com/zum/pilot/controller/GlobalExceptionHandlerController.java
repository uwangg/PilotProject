package com.zum.pilot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandlerController {

  @ExceptionHandler(value = NoHandlerFoundException.class)
  public String handleNotFoundException(Exception e) {
    e.printStackTrace();
    return "error/404";
  }

  @ExceptionHandler(value = NullPointerException.class)
  public String handleNullPointerException(Exception e) {
    e.printStackTrace();
    return "error/errorNull";
  }
}
