package com.rara.my_blog.controller;

import com.rara.my_blog.dto.ResponseDto;
import com.rara.my_blog.dto.SignupRequestDto;
import com.rara.my_blog.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public ResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResponseDto("회원가입 실패", HttpStatus.BAD_REQUEST.value());
		}
		return userService.signup(signupRequestDto);
	}
}
