package com.rara.my_blog.service.impl;

import com.rara.my_blog.dto.CommentRequestDto;
import com.rara.my_blog.dto.CommentResponseDto;
import com.rara.my_blog.dto.UserRoleEnum;
import com.rara.my_blog.entity.Comment;
import com.rara.my_blog.entity.User;
import com.rara.my_blog.exception.CustomException;
import com.rara.my_blog.exception.ErrorCode;
import com.rara.my_blog.jwt.JwtUtil;
import com.rara.my_blog.repository.CommentRepository;
import com.rara.my_blog.repository.PostRepository;
import com.rara.my_blog.repository.UserRepository;
import com.rara.my_blog.service.CommentService;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final JwtUtil jwtUtil;
	private final UserRepository userRepository;

	@Override
	public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto,
		HttpServletRequest httpServletRequest) {
		User user = getUserInfo(httpServletRequest);

		if(postRepository.existsById(id)) {
			Comment comment = new Comment(commentRequestDto, user.getUsername());
			commentRepository.save(comment);
			comment.setPost(postRepository.findById(id).get());
			return new CommentResponseDto(comment);
		} else {
			throw new CustomException(ErrorCode.NOT_FOUND_POST);
		}
	}

	@Override
	public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto,
		HttpServletRequest httpServletRequest) {
		User user = getUserInfo(httpServletRequest);

		if (commentRepository.existsByIdAndUsername(id, user.getUsername()) || user.getRole().equals(
			UserRoleEnum.ADMIN)) {
			Comment comment = commentRepository.findById(id).get();
			comment.update(commentRequestDto, user.getUsername());
			return new CommentResponseDto(comment);
		} else {
			throw new CustomException(ErrorCode.UNAVAILABLE_MODIFICATION);
		}
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
				throw new CustomException(ErrorCode.INVALID_TOKEN);
			}

			// 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
			return userRepository.findByUsername(claims.getSubject()).orElseThrow(
				() -> new CustomException(ErrorCode.USER_NOT_FOUND)
			);
		} else {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}
	}
}