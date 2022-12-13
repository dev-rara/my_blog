package com.rara.my_blog.service.impl;

import com.rara.my_blog.dto.PostRequestDto;
import com.rara.my_blog.dto.PostResponseDto;
import com.rara.my_blog.dto.ResponseDto;
import com.rara.my_blog.dto.UserRoleEnum;
import com.rara.my_blog.entity.Post;
import com.rara.my_blog.entity.User;
import com.rara.my_blog.exception.CustomException;
import com.rara.my_blog.exception.ErrorCode;
import com.rara.my_blog.jwt.JwtUtil;
import com.rara.my_blog.repository.PostRepository;
import com.rara.my_blog.repository.UserRepository;
import com.rara.my_blog.service.PostService;
import com.rara.my_blog.util.UserUtil;
import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final UserUtil userUtil;


	@Override
	@Transactional
	public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest httpServletRequest) {
		User user = userUtil.getUserInfo(httpServletRequest);

		//유효한 토큰일 경우 게시글 등록
		Post post = new Post(requestDto.getTitle(), user.getUsername(), requestDto.getContent());
		post.setUser(user);
		post = postRepository.save(post);
		return new PostResponseDto(post);
	}


	@Override
	@Transactional(readOnly = true)
	public List<PostResponseDto> getPostList() {
		return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).collect(
			Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public PostResponseDto getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND_POST)
		);
		return new PostResponseDto(post);
	}


	@Override
	@Transactional
	public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest httpServletRequest) {
		User user = userUtil.getUserInfo(httpServletRequest);

		//유효한 토큰이거나 AMIN 권한일 경우 수정
		if (postRepository.existsByIdAndUsername(id, user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
			Post post = postRepository.findById(id).get();
			post.update(requestDto.getTitle(), user.getUsername(), requestDto.getContent());
			return new PostResponseDto(post);
		} else {
			throw new CustomException(ErrorCode.UNAVAILABLE_MODIFICATION);
		}
	}


	@Override
	public ResponseDto deletePost(Long id, HttpServletRequest httpServletRequest) {
		User user = userUtil.getUserInfo(httpServletRequest);

		//유효한 토큰이거나 AMIN 권한일 경우 삭제
		if (postRepository.existsByIdAndUsername(id, user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
			postRepository.deleteById(id);
			return new ResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
		} else {
			throw new CustomException(ErrorCode.UNAVAILABLE_MODIFICATION);
		}
	}

}
