package com.rara.my_blog.repository;

import com.rara.my_blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


	boolean existsByIdAndUsername(Long id, String username);
}
