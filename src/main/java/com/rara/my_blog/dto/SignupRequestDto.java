package com.rara.my_blog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

	@NotBlank(message = "아이디는 필수 입력값 입니다.")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z]).{4,10}$", message = "아이디는 알파벳 소문자와 숫자로 구성된 4~20자리여야 합니다.")
	private String username;

	@NotBlank(message = "비밀번호는 필수 입력값 입니다.")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&].{8,15}$", message = "비밀번호는 알파벳 대소문자와 숫자, 특수문자로 구성된 8~15자리여야 합니다.")
	private String password;

	private boolean admin = false;

	private String adminToken = "";
}
