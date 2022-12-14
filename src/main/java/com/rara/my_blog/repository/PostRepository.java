package com.rara.my_blog.repository;

import com.rara.my_blog.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	Optional<Post> findById(Long id);

	boolean existsByIdAndUsername(Long id, String username);

	List<Post> findAllByOrderByModifiedAtDesc();
}
