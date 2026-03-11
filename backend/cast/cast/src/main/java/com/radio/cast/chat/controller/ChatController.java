package com.radio.cast.chat.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.radio.cast.chat.dto.ChatMessageDto;
import com.radio.cast.chat.service.ChatService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
  private final ChatService chatService;
  private final SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/chat/send")
  public void sendMessage(ChatMessageDto message, Principal principal) {
    // System.out.println("채팅 내역 : " + message.getRadioChannelId());
    // System.out.println("채팅 내역 : " + message.getContent());
    // System.out.println("채팅 내역 : " + message.getSender());
    //토큰에서 사용자 이름 추출
    String username = principal.getName();
    message.setSender(username);

    ChatMessageDto savedMessage = chatService.saveMessage(message.getRadioChannelId(), message);

    simpMessagingTemplate.convertAndSend(
      "/topic/chatChannel/" + message.getRadioChannelId(), savedMessage
    );
  }

  /**
   * 특정 채팅방 채팅 20개 조회
   * @param radioChannelId
   * @return
   */
  @GetMapping("/history")
  public List<ChatMessageDto> chatHistory(@RequestParam Long radioChannelId) {
      return chatService.chatHistory(radioChannelId);
  }
  
}
