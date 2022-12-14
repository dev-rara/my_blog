package com.rara.my_blog.service.impl;

import com.rara.my_blog.dto.CommentRequestDto;
import com.rara.my_blog.dto.CommentResponseDto;
import com.rara.my_blog.dto.ResponseDto;
import com.rara.my_blog.dto.UserRoleEnum;
import com.rara.my_blog.entity.Comment;
import com.rara.my_blog.entity.Post;
import com.rara.my_blog.entity.User;
import com.rara.my_blog.exception.CustomException;
import com.rara.my_blog.exception.ErrorCode;
import com.rara.my_blog.jwt.JwtUtil;
import com.rara.my_blog.repository.CommentRepository;
import com.rara.my_blog.repository.PostRepository;
import com.rara.my_blog.repository.UserRepository;
import com.rara.my_blog.service.CommentService;
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
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final UserUtil userUtil;


	@Override
	@Transactional
	public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto,
		HttpServletRequest httpServletRequest) {
		User user = userUtil.getUserInfo(httpServletRequest);

		if(postRepository.existsById(id)) {
			Post post = postRepository.findById(id).get();
			Comment comment = new Comment(commentRequestDto.getContent(), user.getUsername());
			comment.setUser(user);
			comment.setPost(post);
			commentRepository.save(comment);
			return new CommentResponseDto(comment);
		} else {
			throw new CustomException(ErrorCode.NOT_FOUND_POST);
		}
	}

	@Override
	@Transactional
	public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto,
		HttpServletRequest httpServletRequest) {
		User user = userUtil.getUserInfo(httpServletRequest);

		Comment comment = commentRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
		);

		//????????? ??????????????? AMIN ????????? ?????? ??????
		if (comment.getUsername().equals(user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
			comment.update(commentRequestDto.getContent(), user.getUsername());
			return new CommentResponseDto(comment);
		} else {
			throw new CustomException(ErrorCode.UNAVAILABLE_MODIFICATION);
		}
	}

	@Override
	@Transactional
	public ResponseDto deleteComment(Long id, HttpServletRequest httpServletRequest) {
		User user = userUtil.getUserInfo(httpServletRequest);

		Comment comment = commentRepository.findById(id).orElseThrow(
			() -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
		);

		//????????? ??????????????? AMIN ????????? ?????? ??????
		if (comment.getUsername().equals(user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
			commentRepository.deleteById(id);
			return new ResponseDto("?????? ?????? ??????", HttpStatus.OK.value());
		} else {
			throw new CustomException(ErrorCode.UNAVAILABLE_MODIFICATION);
		}
	}

}
