package com.rara.my_blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	@Pattern(regexp = "^[a-z0-9]{4,10}$", message = "알파벳 소문자와 숫자로 구성된 4~20자리여야 합니다.")
	private String username;


	@Column(nullable = false)
	@Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "알파벳 대소문자와 숫자로 구성된 8~15자리여야 합니다.")
	private String password;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

}
