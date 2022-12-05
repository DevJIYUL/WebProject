//package com.home.advice;
//
//
//import javax.security.sasl.AuthenticationException;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//
//import com.home.vo.msg.ErrorMessage;
//
//@RestControllerAdvice
//public class TokenControllerAdvice {
//
//  @ExceptionHandler(value = AuthenticationException.class)
//  @ResponseStatus(HttpStatus.UNAUTHORIZED)
//  public ErrorMessage handleTokenRefreshException(AuthenticationException ex, WebRequest request) {
//    return new ErrorMessage(ex.getMessage());
//  }
//}