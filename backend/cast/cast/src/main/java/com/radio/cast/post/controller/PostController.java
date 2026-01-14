package com.radio.cast.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.radio.cast.post.dto.CursorResponse;
import com.radio.cast.post.dto.PostDetailResponse;
import com.radio.cast.post.dto.PostListResponse;
import com.radio.cast.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
  private final PostService postService;

  /**
   * 게시글 리스트 조회(무한 스크롤) 컨트롤러
   * @param cursor
   * @param size
   * @return
   */
  @GetMapping("/list")
  public ResponseEntity<CursorResponse<PostListResponse>> postList(
      @RequestParam(required = false) Long cursor,
      @RequestParam(required = false, defaultValue = "10") Integer size){
    CursorResponse<PostListResponse> postData = postService.postDate(cursor, size);
    return ResponseEntity.ok(postData);
  }

  /**
   * 게시글 상세 페이지 컨트롤러
   * @param postId
   * @return
   */
  @GetMapping("/detail")
  public ResponseEntity<PostDetailResponse> postDetail(@RequestParam Long postId){
    return ResponseEntity.ok(postService.postDetail(postId));
  }
}
