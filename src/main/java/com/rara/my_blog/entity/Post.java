package com.rara.my_blog.entity;

import com.rara.my_blog.dto.PostRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;


	public Post(PostRequestDto requestDto, String username) {
		this.title = requestDto.getTitle();
		this.username = username;
		this.content = requestDto.getContent();
	}

	public void update(PostRequestDto requestDto, String username) {
		this.title = requestDto.getTitle();
		this.username = username;
		this.content = requestDto.getContent();
	}
}
