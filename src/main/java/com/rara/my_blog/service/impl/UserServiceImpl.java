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
import com.rara.my_blog.util.UserUtil;
import io.jsonwebtoken.Claims;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtUtil jwtUtil;
	private final UserUtil userUtil;

	@Value("${jwt.admin.token}")
	private String adminToken;

	@Override
	@Transactional
	public ResponseDto signup(SignupRequestDto signupRequestDto) {
		String username = signupRequestDto.getUsername();
		String password = passwordEncoder.encode(signupRequestDto.getPassword());

		//회원 중복 확인
		if (userRepository.existsByUsername(username)) {
			throw new CustomException(ErrorCode.OVERLAP_USERNAME);
		}

		//사용자 ROLE 확인
		UserRoleEnum role = UserRoleEnum.USER;
		if (signupRequestDto.isAdmin()) {
			if (!signupRequestDto.getAdminToken().equals(adminToken)) {
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
		//사용자 확인
		User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(
			() -> new CustomException(ErrorCode.USER_NOT_FOUND)
		);

		//비밀번호 확인
		if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
			throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
		}

		//1. Login ID/PW 를 기반으로 AuthenticationToken 생성
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());

		//2. 실제로 검증(사용자 비밀번호 체크)이 이루어지는 부분
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		//3. 토큰을 생성하여 Header 에 저장
		httpServletResponse.addHeader(
			JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(authentication));

		return new ResponseDto("로그인 성공", HttpStatus.OK.value());
	}

	@Override
	@Transactional
	public ResponseDto delete(DeleteRequestDto deleteRequestDto, HttpServletRequest httpServletRequest) {
		User user = userUtil.getUserInfo(httpServletRequest);

		if (passwordEncoder.matches(deleteRequestDto.getPassword(), user.getPassword())) {
			userRepository.deleteByUsername(user.getUsername());
			SecurityContextHolder.clearContext();
			return new ResponseDto("회원탈퇴 성공", HttpStatus.OK.value());
		} else {
			throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
		}
	}

}
