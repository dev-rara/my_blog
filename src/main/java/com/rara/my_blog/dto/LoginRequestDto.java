package com.rara.my_blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
	private String username;
	private String password;
}
