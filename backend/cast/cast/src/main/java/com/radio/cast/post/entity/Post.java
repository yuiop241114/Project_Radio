package com.radio.cast.post.entity;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Post")
@Builder
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;

  @Column(nullable = false)
  private String postTitle;

  @Lob
  private String postContent;

  @Column(nullable = false)
  private String postAuthor;

  @Column(nullable = false)
  private LocalDateTime postDate;

  @Column(nullable = true)
  private Long postView;

  /**
   * 게시글 수정 엔티티 메소드
   * @param postTitle
   * @param postContent
   */
  public void update(String postTitle, String postContent){
    this.postTitle = postTitle;
    this.postContent = postContent;
    this.postDate = LocalDateTime.now(); 
  }

  /**
   * 게시글 생성 엔티티 메소드
   * @param postTitle
   * @param postContent
   * @param postAuthor
   */
  @Builder
  public Post(String postTitle, String postContent, String postAuthor) {
      this.postTitle = postTitle;
      this.postContent = postContent;
      this.postAuthor = postAuthor;
      this.postDate = LocalDateTime.now(); 
      this.postView = (long) 0;
  }
}
