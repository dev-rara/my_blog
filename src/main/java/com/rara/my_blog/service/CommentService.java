package com.rara.my_blog.service;

import com.rara.my_blog.dto.CommentRequestDto;
import com.rara.my_blog.dto.CommentResponseDto;
import javax.servlet.http.HttpServletRequest;

public interface CommentService {
	CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest httpServletRequest);
}
