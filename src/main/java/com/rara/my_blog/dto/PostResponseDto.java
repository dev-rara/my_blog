package com.rara.my_blog.dto;

import com.rara.my_blog.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponseDto {
	private Long id;
	private String title;
	private String username;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	private List<CommentResponseDto> comments;

	public PostResponseDto(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.username = post.getUsername();
		this.content = post.getContent();
		this.createdAt = post.getCreatedAt();
		this.modifiedAt = post.getModifiedAt();
	}

	public PostResponseDto (Post post, List<CommentResponseDto> comments) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.username = post.getUsername();
		this.content = post.getContent();
		this.comments = comments;
		this.createdAt = post.getCreatedAt();
		this.modifiedAt = post.getModifiedAt();
	}
}
