package com.radio.cast.post.dto;

import java.util.Date;

import com.radio.cast.post.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostListResponse {
  private Long postId;
  private String postTitle;
  private String postContent;
  private String postAuthor;
  private Date postDate;
  private Long postView;
}

