package com.rara.my_blog.service.impl;

import com.rara.my_blog.dto.DeleteRequestDto;
import com.rara.my_blog.dto.LoginRequestDto;
import com.rara.my_blog.dto.ResponseDto;
import com.rara.my_blog.dto.SignupRequestDto;
import com.rara.my_blog.dto.UserRoleEnum;
import com.rara.my_blog.entity.User;
import com.rara.my_blog.exception.CustomException;
import com.rara.my_blog.exception.ErrorCode;
import com.rara.my_blog.jwt.JwtUtil;
import com.rara.my_blog.repository.UserRepository;
import com.rara.my_blog.service.UserService;
import io.jsonwebtoken.Claims;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final JwtUtil jwtUtil;

	private final PasswordEncoder passwordEncoder;

	private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

	@Override
	@Transactional
	public ResponseDto signup(SignupRequestDto signupRequestDto) {
		String username = signupRequestDto.getUsername();
		String password = passwordEncoder.encode(signupRequestDto.getPassword());

		//회원 중복 확인
		Optional<User> found = userRepository.findByUsername(username);
		if(found.isPresent()) {
			throw new CustomException(ErrorCode.OVERLAP_USERNAME);
		}

		//사용자 ROLE 확인
		UserRoleEnum role = UserRoleEnum.USER;
		if (signupRequestDto.isAdmin()) {
			if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
				throw new CustomException(ErrorCode.MISMATCH_ADMIN_TOKEN);
			}
			role = UserRoleEnum.ADMIN;
		}

		User user = new User(username, password, role);
		userRepository.save(user);

		return new ResponseDto("회원가입 성공", HttpStatus.OK.value());
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
		String username = loginRequestDto.getUsername();
		String password = loginRequestDto.getPassword();

		//사용자 확인
		User user = userRepository.findByUsername(username).orElseThrow(
			() -> new CustomException(ErrorCode.USER_NOT_FOUND)
		);

		//비밀번호 확인
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
		}

		httpServletResponse.addHeader(
			JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

		return new ResponseDto("로그인 성공", HttpStatus.OK.value());
	}

	@Override
	@Transactional
	public ResponseDto delete(DeleteRequestDto deleteRequestDto, HttpServletRequest httpServletRequest) {
		User user = getUserInfo(httpServletRequest);

		if (passwordEncoder.matches(deleteRequestDto.getPassword(), user.getPassword())) {
			userRepository.deleteByUsername(user.getUsername());
			SecurityContextHolder.clearContext();
			return new ResponseDto("회원탈퇴 성공", HttpStatus.OK.value());
		} else {
			throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
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
			User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
				() -> new CustomException(ErrorCode.USER_NOT_FOUND)
			);
			return user;
		} else {
			throw new CustomException(ErrorCode.INVALID_TOKEN);
		}
	}
}
