package com.radio.cast.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radio.cast.post.dto.PostListResponse;
import com.radio.cast.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
  Optional<Post> findById(Long postId);
}
