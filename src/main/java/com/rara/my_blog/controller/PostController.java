package com.rara.my_blog.controller;

import com.rara.my_blog.dto.PostDeleteRequestDto;
import com.rara.my_blog.dto.PostDeleteResponseDto;
import com.rara.my_blog.dto.PostRequestDto;
import com.rara.my_blog.dto.PostResponseDto;
import com.rara.my_blog.entity.Post;
import com.rara.my_blog.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;

	@PostMapping("/api/posts")    // 게시글 작성 API
	public PostResponseDto createPost(@RequestBody PostRequestDto requestDto) {
		return postService.createPost(requestDto);
	}

	@GetMapping("/api/posts")     // 전체 게시글 목록 조회 API
	public List<PostResponseDto> getPostList() {
		return postService.getPostList();
	}

	@GetMapping("/api/posts/{id}")   // 선택한 게시글 조회 API
	public PostResponseDto getPost(@PathVariable Long id) {
		return postService.getPost(id);
	}

	@PutMapping("/api/posts/{id}")   // 선택한 게시글 수정 API
	public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
		return postService.update(id, requestDto);
	}

	@DeleteMapping("/api/posts/{id}")   // 선택한 게시글 삭제 API
	public PostDeleteResponseDto deletePost(@PathVariable Long id, @RequestBody PostDeleteRequestDto requestDto) {
		return new PostDeleteResponseDto(postService.deletePost(id, requestDto.getPassword()));
	}
}
