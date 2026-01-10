package com.radio.cast.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radio.cast.post.dto.PostListResponse;
import com.radio.cast.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
  /**
   * 첫 조회
   * @return
   */
  List<Post> findTop20ByOrderByPostIdDesc();

  /**
   * 다음 조회
   * @param cursor
   * @return
   */
  List<Post> findTop20ByPostIdLessThanOrderByPostIdDesc(Long cursor);
}
