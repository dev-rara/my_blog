package com.rara.my_blog.service;

import com.rara.my_blog.dto.ResponseDto;
import com.rara.my_blog.dto.SignupRequestDto;

public interface UserService {
	ResponseDto signup(SignupRequestDto signupRequestDto);
}
