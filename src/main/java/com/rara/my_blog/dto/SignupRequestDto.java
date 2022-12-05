package com.rara.my_blog.dto;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z]).{4,10}$", message = "알파벳 소문자와 숫자로 구성된 4~20자리여야 합니다.")
	private String username;

	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}$", message = "알파벳 대소문자, 숫자로 구성된 8~15자리여야 합니다.")
	private String password;
}
