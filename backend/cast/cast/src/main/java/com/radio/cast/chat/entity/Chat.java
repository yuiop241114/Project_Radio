package com.radio.cast.chat.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Chat {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long chatId;

  private Long radioChannelId;

  private String sender;

  private String content;

  private LocalDateTime createdAt;

  public Chat(Long radioChannelId, String sender, String content) {
      this.radioChannelId = radioChannelId;
      this.sender = sender;
      this.content = content;
      this.createdAt = LocalDateTime.now();
  }
}
