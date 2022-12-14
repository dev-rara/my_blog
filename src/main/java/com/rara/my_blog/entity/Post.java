package com.rara.my_blog.entity;

import com.rara.my_blog.dto.PostRequestDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	public Post(String title, String username, String content) {
		this.title = title;
		this.username = username;
		this.content = content;
	}

	public void setUser(User user) {
		if (this.user != null) {
			this.user.getComments().remove(this);
		}
		this.user = user;

		if (user.getPosts().contains(this)) {
			user.getPosts().add(this);
		}
	}

	public void update(String title, String username, String content) {
		this.title = title;
		this.username = username;
		this.content = content;
	}

}
