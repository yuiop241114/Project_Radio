package com.radio.cast.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.radio.cast.post.dto.CursorResponse;
import com.radio.cast.post.dto.PostListResponse;
import com.radio.cast.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
  private PostService postService;

  @GetMapping("/list")
  public ResponseEntity<CursorResponse<PostListResponse>> postList(
      @RequestParam(required = false) Long cursor,
      @RequestParam(required = false, defaultValue = "20") Integer size){
    CursorResponse<PostListResponse> postData = postService.postDate(cursor, size);
    return ResponseEntity.ok(postData);
  }
}
