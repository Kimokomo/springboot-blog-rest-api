package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;


// We throw this Exception whenever we write some business logic or validate request parameters
public class BlogAPIException extends RuntimeException{
  private HttpStatus status;
  private String message;

  public BlogAPIException(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
