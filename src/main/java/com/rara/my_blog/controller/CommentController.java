package com.rara.my_blog.controller;

import com.rara.my_blog.dto.CommentRequestDto;
import com.rara.my_blog.dto.CommentResponseDto;
import com.rara.my_blog.service.CommentService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
	private final CommentService commentService;

	@PostMapping("/{id}")
	public CommentResponseDto createComment(@PathVariable Long id,
		@RequestBody CommentRequestDto commentRequestDto,
		HttpServletRequest httpServletRequest) {
		return commentService.createComment(id, commentRequestDto, httpServletRequest);
	}

	@PutMapping("{id}")
	public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto,
		HttpServletRequest httpServletRequest) {
		return commentService.updateComment(id, commentRequestDto, httpServletRequest);
	}

}
