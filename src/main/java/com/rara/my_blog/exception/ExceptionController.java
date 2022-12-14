package com.rara.my_blog.exception;

import com.rara.my_blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

	//CustomException
	@ExceptionHandler(CustomException.class)
	public ResponseDto exception(CustomException e) {
		return new ResponseDto(e.getMsg(), e.getStatusCode());
	}


	//Valid Exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseDto handleValidationExceptions(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		StringBuilder sb = new StringBuilder();
		bindingResult.getAllErrors()
			.forEach(objectError -> sb.append(objectError.getDefaultMessage()));

		return new ResponseDto(sb.toString(), HttpStatus.BAD_REQUEST.value());
	}
}