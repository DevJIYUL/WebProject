//package com.home.exception;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class ExceptionHandler {
//	private final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
//	
//	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
//	public ResponseEntity<String> handleException(Exception ex) {
//		logger.debug("error!!!!"+ex.getClass()+" " + ex.getMessage());
//		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//	}
//}
