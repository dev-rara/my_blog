package com.rara.my_blog.service.impl;

import com.rara.my_blog.dto.LoginRequestDto;
import com.rara.my_blog.dto.ResponseDto;
import com.rara.my_blog.dto.SignupRequestDto;
import com.rara.my_blog.entity.User;
import com.rara.my_blog.jwt.JwtUtil;
import com.rara.my_blog.repository.UserRepository;
import com.rara.my_blog.service.UserService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final JwtUtil jwtUtil;

	@Override
	@Transactional
	public ResponseDto signup(SignupRequestDto signupRequestDto) {
		String username = signupRequestDto.getUsername();
		String password = signupRequestDto.getPassword();

		Optional<User> found = userRepository.findByUsername(username);
		if(found.isPresent()) {
			throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
		}

		User user = new User(username, password);
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
			() -> new IllegalArgumentException("등록된 사용자가 없습니다.")
		);

		//비밀번호 확인
		if(!user.getPassword().equals(password)) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		httpServletResponse.addHeader(
			JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));

		return new ResponseDto("로그인 성공", HttpStatus.OK.value());
	}
}
