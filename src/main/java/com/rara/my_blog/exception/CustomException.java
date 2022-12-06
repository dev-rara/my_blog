package com.rara.my_blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomException extends RuntimeException{
	private int statusCode;
	private String msg;

	public CustomException(ErrorCode errorCode) {
		this.msg = errorCode.getMsg();
		this.statusCode = errorCode.getStatusCode();
	}
}
