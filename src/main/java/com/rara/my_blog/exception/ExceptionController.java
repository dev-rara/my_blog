package com.rara.my_blog.exception;

import com.rara.my_blog.dto.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
	//CustomException
	@ExceptionHandler(value = CustomException.class)
	public ResponseDto exception(CustomException e) {
		return new ResponseDto(e.getMsg(), e.getStatusCode());
	}


	//그 외의 Exception
	@ExceptionHandler(Exception.class)
	public ResponseDto ValidException(Exception e) {
		return new ResponseDto(ErrorCode.SERVER_ERROR.getMsg(), ErrorCode.SERVER_ERROR.getStatusCode());
	}
}