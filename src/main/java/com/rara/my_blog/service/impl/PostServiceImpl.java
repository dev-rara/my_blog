package com.rara.my_blog.service.impl;

import com.rara.my_blog.dto.PostRequestDto;
import com.rara.my_blog.dto.PostResponseDto;
import com.rara.my_blog.entity.Post;
import com.rara.my_blog.repository.PostRepository;
import com.rara.my_blog.service.PostService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	@Override
	@Transactional
	public PostResponseDto createPost(PostRequestDto requestDto) {
		Post post = new Post(requestDto);
		postRepository.save(post);
		return new PostResponseDto(post);
	}


	@Override
	@Transactional(readOnly = true)
	public List<PostResponseDto> getPostList(Pageable pageable) {
		return postRepository.findAllByOrderByCreatedAtDesc(pageable).stream().map(PostResponseDto::new).collect(
			Collectors.toList());
	}

	@Override
	@Transactional
	public PostResponseDto getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
		);
		return new PostResponseDto(post);
	}


	@Override
	@Transactional
	public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
		Post post = postRepository.findByIdAndPassword(id, requestDto.getPassword()).orElseThrow(
			() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")
		);
		post.update(requestDto);
		postRepository.save(post);

		return new PostResponseDto(post);
	}

	@Override
	public boolean deletePost(Long id, String password) {
		if (postRepository.existsByIdAndPassword(id, password)) {
			postRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
