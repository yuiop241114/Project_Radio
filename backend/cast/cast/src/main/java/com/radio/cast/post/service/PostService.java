package com.radio.cast.post.service;

import org.springframework.stereotype.Service;

import com.radio.cast.post.dto.PostListResponse;
import com.radio.cast.post.entity.Post;
import com.radio.cast.post.repository.PostRepository;

@Service
public class PostService {
  private PostRepository postRepository;

  public Post postList(Long postId) {
    return postRepository.findById(postId);
  }
  
}
