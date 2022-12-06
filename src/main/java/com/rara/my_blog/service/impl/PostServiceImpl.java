package com.rara.my_blog.service.impl;

import com.rara.my_blog.dto.PostRequestDto;
import com.rara.my_blog.dto.PostResponseDto;
import com.rara.my_blog.dto.ResponseDto;
import com.rara.my_blog.entity.Post;
import com.rara.my_blog.entity.User;
import com.rara.my_blog.jwt.JwtUtil;
import com.rara.my_blog.repository.PostRepository;
import com.rara.my_blog.repository.UserRepository;
import com.rara.my_blog.service.PostService;
import io.jsonwebtoken.Claims;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;

	@Override
	@Transactional
	public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest httpServletRequest) {
		User user = getUserInfo(httpServletRequest);

		if(user == null) {
			throw new IllegalArgumentException("유효한 Token이 아닙니다.");
		}

		//유효한 토큰일 경우 게시글 등록
		Post post = new Post(requestDto, user.getUsername());
		post = postRepository.save(post);
		post.setUser(user);
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
			() -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
		);
		return new PostResponseDto(post);
	}


	@Override
	@Transactional
	public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest httpServletRequest) {
		User user = getUserInfo(httpServletRequest);

		if(user == null) {
			throw new IllegalArgumentException("유효한 Token이 아닙니다.");
		}

		//유효한 토큰일 경우 게시글 수정
		Post post = postRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("해당하는 게시글이 없습니다.")
		);
		post.update(requestDto, user.getUsername());
		postRepository.save(post);

		return new PostResponseDto(post);
	}


	@Override
	public ResponseDto deletePost(Long id, HttpServletRequest httpServletRequest) {
		User user = getUserInfo(httpServletRequest);

		if (user == null) {
			throw new IllegalArgumentException("유효한 Token이 아닙니다.");
		}

		//유효한 토큰일 경우 삭제
		if(postRepository.existsByIdAndUsername(id, user.getUsername())) {
			postRepository.deleteById(id);
			return new ResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
		}
		return null;
	}


	private User getUserInfo(HttpServletRequest httpServletRequest) {
		String token = jwtUtil.resolveToken(httpServletRequest);
		Claims claims;

		//유효한 토큰일 경우 수정 가능
		if (token != null) {
			if (jwtUtil.validateToken(token)) {
				// 토큰에서 사용자 정보 가져오기
				claims = jwtUtil.getUserInfoFromToken(token);
			} else {
				throw new IllegalArgumentException("유효한 Token이 아닙니다.");
			}

			// 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
			User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
				() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
			);
			return user;
		}
		return null;
	}
}
