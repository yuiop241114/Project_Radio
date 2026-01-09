package com.radio.cast.post.dto;

import java.time.LocalDateTime;
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
  private LocalDateTime postDate; //LocalDateTime : 해당 시간 입력, 시분초 까지 나옴
  private Long postView;

  public PostListResponse(Post post) {
        this.postId = post.getPostId();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.postAuthor = post.getPostAuthor();
        this.postDate = post.getPostDate(); // 엔티티의 날짜 필드명에 맞춰 수정하세요
        this.postView = post.getPostView();   // 엔티티의 조회수 필드명에 맞춰 수정하세요
    }
}

