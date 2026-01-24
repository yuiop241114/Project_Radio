package com.radio.cast.post.dto;

import com.radio.cast.post.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostEditRequest {
  private Long postId; 
  private String postTitle;
  private String postContent;

  public Post toEntity(){
    return Post.builder()
               .postId(postId)
               .postTitle(postTitle)
               .postContent(postContent)
               .build();
  }
}
