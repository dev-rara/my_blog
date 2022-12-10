package com.rara.my_blog.dto;

import com.rara.my_blog.entity.Post;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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

	private List<CommentResponseDto> comments = new ArrayList<>();

	public PostResponseDto (Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.username = post.getUsername();
		this.content = post.getContent();
		this.comments = post.getComments().stream().map(CommentResponseDto::new)
			.sorted(Comparator.comparing(CommentResponseDto::getCreatedAt).reversed())
			.collect(Collectors.toList());
		this.createdAt = post.getCreatedAt();
		this.modifiedAt = post.getModifiedAt();
	}
}
