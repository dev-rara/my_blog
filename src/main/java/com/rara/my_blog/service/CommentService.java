package com.rara.my_blog.service;

import com.rara.my_blog.dto.CommentRequestDto;
import com.rara.my_blog.dto.CommentResponseDto;
import com.rara.my_blog.dto.ResponseDto;
import javax.servlet.http.HttpServletRequest;

public interface CommentService {
	//댓글 작성
	CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest);

	//댓글 수정
	CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest);

	//댓글 수정
	ResponseDto deleteComment(Long id, HttpServletRequest httpServletRequest);
}
