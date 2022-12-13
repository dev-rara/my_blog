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

	public Comment(String content, String username) {
		this.content = content;
		this.username = username;
	}

	public void setPost(Post post) {
		if (this.post != null) {
			this.post.getComments().remove(this);
		}
		this.post = post;

		if (!post.getComments().contains(this)) {
			post.getComments().add(this);
		}
	}

	public void setUser(User user) {
		if (this.user != null) {
			this.user.getComments().remove(this);
		}
		this.user = user;

		if (!user.getComments().contains(this)) {
			user.getComments().add(this);
		}
	}

	public void update(String content, String username) {
		this.content = content;
		this.username = username;
	}

}
