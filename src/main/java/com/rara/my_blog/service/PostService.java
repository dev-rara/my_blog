package com.rara.my_blog.service;

import com.rara.my_blog.dto.PostRequestDto;
import com.rara.my_blog.dto.PostResponseDto;
import java.util.List;

public interface PostService {

	// 게시글 생성
	PostResponseDto createPost(PostRequestDto requestDto);

	// 게시글 전체 목록 조회
	List<PostResponseDto> getPostList();

	// 선택한 게시글 조회
	PostResponseDto getPost(Long id);

	// 선택한 게시글 수정
	PostResponseDto updatePost(Long id, PostRequestDto requestDto);

	// 선택한 게시글 삭제
	boolean deletePost(Long id, String password);

}
