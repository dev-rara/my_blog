package com.rara.my_blog.controller;

import com.rara.my_blog.dto.ResponseDto;
import com.rara.my_blog.dto.SignupRequestDto;
import com.rara.my_blog.service.UserService;
import lombok.RequiredArgsConstructor;
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
	public ResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
		return userService.signup(signupRequestDto);
	}
}
