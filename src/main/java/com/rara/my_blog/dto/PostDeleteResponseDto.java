package com.rara.my_blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PostDeleteResponseDto {
	private boolean success;

	public PostDeleteResponseDto (boolean result) {
		this.success = result;
	}
}
