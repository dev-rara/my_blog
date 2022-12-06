package com.rara.my_blog.service.impl;

import com.rara.my_blog.dto.PostRequestDto;
import com.rara.my_blog.dto.PostResponseDto;
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
import org.springframework.data.domain.Pageable;
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
		String token = jwtUtil.resolveToken(httpServletRequest);
		Claims claims;

		//유효한 토큰일 경우에만 게시글 작성
		if(token != null) {
			if(jwtUtil.validateToken(token)) {
				//사용자 정보 가져오기
				claims = jwtUtil.getUserInfoFromToken(token);
			} else {
				throw new IllegalArgumentException("유효한 Token이 아닙니다.");
			}

			//토큰에서 가져온 사용자 정보로 DB 조최
			User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
				() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
			);

			Post post = new Post(requestDto, user.getUsername());
			post = postRepository.save(post);
			post.setUser(user);
			return new PostResponseDto(post);
		} else {
			return null;
		}
	}


	@Override
	@Transactional(readOnly = true)
	public List<PostResponseDto> getPostList(Pageable pageable) {
		return postRepository.findAllByOrderByCreatedAtDesc(pageable).stream().map(PostResponseDto::new).collect(
			Collectors.toList());
	}

	@Override
	@Transactional
	public PostResponseDto getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
		);
		return new PostResponseDto(post);
	}


	@Override
	@Transactional
	public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest httpServletRequest) {
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

			Post post = postRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("해당하는 글이 없습니다.")
			);
			post.update(requestDto, user.getUsername());
			postRepository.save(post);

			return new PostResponseDto(post);
		}
		return null;
	}

	@Override
	public boolean deletePost(Long id, String password) {
//		if (postRepository.existsByIdAndPassword(id, password)) {
//			postRepository.deleteById(id);
//			return true;
//		}
		return false;
	}
}
