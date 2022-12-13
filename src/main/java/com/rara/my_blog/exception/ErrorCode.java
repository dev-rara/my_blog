package com.rara.my_blog.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	/* 400 BAD_REQUEST : 잘못된 요청 */
	USER_NOT_FOUND(BAD_REQUEST.value(), "회원을 찾을 수 없습니다."),
	OVERLAP_USERNAME(BAD_REQUEST.value(), "중복된 username 입니다."),
	MISMATCH_PASSWORD(BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),
	NOT_FOUND_POST(BAD_REQUEST.value(), "게시글을 찾을 수 없습니다"),
	NOT_FOUND_COMMENT(BAD_REQUEST.value(), "댓글을 찾을 수 없습니다"),
	UNAVAILABLE_MODIFICATION(BAD_REQUEST.value(), "작성자만 삭제/수정할 수 있습니다."),
	MISMATCH_ADMIN_TOKEN(BAD_REQUEST.value(), "관리자 암호가 틀립니다."),
	INVALID_TOKEN(BAD_REQUEST.value(), "토큰이 유효하지 않습니다."),

	/* 500 INTERNAL_SERVER_ERROR : 내부 서버 오류 */
	SERVER_ERROR(INTERNAL_SERVER_ERROR.value(), "내부 서버 오류가 발생했습니다.");

	private final int statusCode;
	private final String msg;
}
