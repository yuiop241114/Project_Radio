package com.radio.cast.chat.dto;

import com.radio.cast.chat.entity.Chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
  private Long radioChannelId;
  private String sender;
  private String content;

  public ChatMessageDto(Chat chat){
    this.radioChannelId = chat.getRadioChannelId();
    this.sender = chat.getSender();
    this.content = chat.getContent();
  }
}
