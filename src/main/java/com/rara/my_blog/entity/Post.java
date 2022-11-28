package com.rara.my_blog.entity;

import com.rara.my_blog.dto.PostRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String author;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private String password;

	public Post(PostRequestDto requestDto) {
		this.title = requestDto.getTitle();
		this.author = requestDto.getAuthor();
		this.content = requestDto.getContent();
		this.password = requestDto.getPassword();
	}

	public void update(PostRequestDto requestDto) {
		this.title = requestDto.getTitle();
		this.author = requestDto.getAuthor();
		this.content = requestDto.getContent();
		this.password = requestDto.getPassword();
	}
}
