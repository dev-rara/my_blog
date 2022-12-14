package com.rara.my_blog.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
	@NotBlank(message = "유저명을 입력해주세요.")
	private String username;
	@NotBlank(message = "비밀번호를 입력해주세요.")
	private String password;
}
