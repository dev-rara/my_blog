package com.rara.my_blog.controller;

import com.rara.my_blog.dto.PostRequestDto;
import com.rara.my_blog.dto.PostResponseDto;
import com.rara.my_blog.dto.ResponseDto;
import com.rara.my_blog.service.PostService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
	private final PostService postService;

	@PostMapping("/posts")    // 게시글 작성 API
	public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest httpServletRequest) {
		return postService.createPost(requestDto, httpServletRequest);
	}

	@GetMapping("/posts")     // 전체 게시글 목록 조회 API
	public List<PostResponseDto> getPostList() {
		return postService.getPostList();
	}

	@GetMapping("/posts/{id}")   // 선택한 게시글 조회 API
	public PostResponseDto getPost(@PathVariable Long id) {
		return postService.getPost(id);
	}

	@PutMapping("/posts/{id}")   // 선택한 게시글 수정 API
	public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest httpServletRequest) {
		return postService.updatePost(id, requestDto, httpServletRequest);
	}

	@DeleteMapping("/posts/{id}")   // 선택한 게시글 삭제 API
	public ResponseDto deletePost(@PathVariable Long id, HttpServletRequest httpServletRequest) {
		return postService.deletePost(id, httpServletRequest);
	}
}
