package com.rara.my_blog.advice;

import com.rara.my_blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
	//발생하는 모든 예외를 핸들링
	@ExceptionHandler(value = Exception.class)
	public ResponseDto exception(Exception e) {
		return new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value());
	}
}