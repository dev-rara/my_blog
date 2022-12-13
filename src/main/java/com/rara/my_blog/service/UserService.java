package com.rara.my_blog.service;

import com.rara.my_blog.dto.DeleteRequestDto;
import com.rara.my_blog.dto.LoginRequestDto;
import com.rara.my_blog.dto.ResponseDto;
import com.rara.my_blog.dto.SignupRequestDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
	ResponseDto signup(SignupRequestDto signupRequestDto);

	ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse);

	ResponseDto delete(DeleteRequestDto deleteRequestDto, HttpServletRequest httpServletRequest);
}
