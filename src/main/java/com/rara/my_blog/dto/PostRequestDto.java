package com.rara.my_blog.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostRequestDto {
	private String title;
	private String author;
	private String content;
	private String password;
	private LocalDateTime registeredAt;
	private LocalDateTime unRegisteredAt;
}
