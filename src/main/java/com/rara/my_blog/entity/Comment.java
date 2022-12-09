package com.rara.my_blog.entity;

import com.rara.my_blog.dto.CommentRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Comment extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Comment(CommentRequestDto requestDto, String username) {
		this.content = requestDto.getContent();
		this.username = username;
	}

	public void setPost(Post post) {
		if (this.post != null) {
			this.post.getComments().remove(this);
		}
		this.post = post;
		post.getComments().add(this);
	}

	public void setUser(User user) {
		if (this.user != null) {
			this.user.getComments().remove(this);
		}
		this.user = user;
		user.getComments().add(this);
	}

	public void update(CommentRequestDto commentRequestDto, String username) {
		this.content = commentRequestDto.getContent();
		this.username = username;
	}

	public void updatePost(Post post) {
		this.post = post;
	}
}
