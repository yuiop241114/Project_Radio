package com.radio.cast.post.dto;

import com.radio.cast.post.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostWriteRequest {
  private String postTitle;
  private String postContent;
  private String postAuthor;

  /**
   * DTO 변환 생성자
   * @param post
   */
  public PostWriteRequest(Post post){
    this.postTitle = post.getPostTitle();
    this.postContent = post.getPostContent();
    this.postAuthor = post.getPostAuthor();
  }
}
