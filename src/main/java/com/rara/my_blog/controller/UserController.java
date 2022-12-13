package com.rara.my_blog.controller;

import com.rara.my_blog.dto.DeleteRequestDto;
import com.rara.my_blog.dto.LoginRequestDto;
import com.rara.my_blog.dto.ResponseDto;
import com.rara.my_blog.dto.SignupRequestDto;
import com.rara.my_blog.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
	private final UserService userService;

	@PostMapping("/signup")
	public ResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			bindingResult.getAllErrors().forEach(objectError -> sb.append(objectError.getDefaultMessage()));

			return new ResponseDto(sb.toString(), HttpStatus.BAD_REQUEST.value());
		}
		return userService.signup(signupRequestDto);
	}

	@PostMapping("/login")
	public ResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
		return userService.login(loginRequestDto, response);
	}

	@DeleteMapping("/withdraw")
	public ResponseDto withdraw(@RequestBody DeleteRequestDto deleteRequestDto, HttpServletRequest httpServletRequest) {
		return userService.delete(deleteRequestDto, httpServletRequest);
	}
}
