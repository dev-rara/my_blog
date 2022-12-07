package com.rara.my_blog.dto;

import com.rara.my_blog.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
	private Long id;
	private String username;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public CommentResponseDto(Comment comment) {
		this.id = comment.getId();
		this.username = comment.getUsername();
		this.content = comment.getContent();
		this.createdAt = comment.getCreatedAt();
		this.modifiedAt = comment.getModifiedAt();
	}
}
