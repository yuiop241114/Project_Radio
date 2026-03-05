package com.radio.cast.chat.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.radio.cast.chat.dto.ChatMessageDto;
import com.radio.cast.chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
  private final ChatService chatService;

  @MessageMapping("/chat/{radioChannelId}")
  @SendTo("/topic/chatChannel/{radioChannelId}")
  public ChatMessageDto sendMessage(
    @DestinationVariable Long radioChannelId,
    ChatMessageDto message
    //, Principal principal
  ) {
      // message.setSender(principal.getName());
      return chatService.saveMessage(radioChannelId, message);
  }
}
