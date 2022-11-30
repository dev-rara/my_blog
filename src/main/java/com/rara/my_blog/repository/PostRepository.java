package com.rara.my_blog.repository;

import com.rara.my_blog.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllByOrderByCreatedAtDesc();

	Optional<Post> findById(Long id);

	Optional<Post> findByIdAndPassword(Long id, String password);

	boolean existsByIdAndPassword(Long id, String password);
}