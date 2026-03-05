package com.radio.cast.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
  private Long radioChannelId;
  private String sender;
  private String content;
}
