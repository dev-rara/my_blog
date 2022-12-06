package com.rara.my_blog.service;

import com.rara.my_blog.dto.PostRequestDto;
import com.rara.my_blog.dto.PostResponseDto;
import com.rara.my_blog.dto.ResponseDto;
import com.rara.my_blog.entity.Post;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface PostService {

	// 게시글 생성
	PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest httpServletRequest);

	// 게시글 전체 목록 조회
	List<PostResponseDto> getPostList();

	// 선택한 게시글 조회
	PostResponseDto getPost(Long id);

	// 선택한 게시글 수정
	PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest httpServletRequest);

	// 선택한 게시글 삭제
	ResponseDto deletePost(Long id, HttpServletRequest httpServletRequest);

}
