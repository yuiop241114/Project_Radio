package com.radio.cast.chat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radio.cast.chat.dto.ChatMessageDto;
import com.radio.cast.chat.entity.Chat;
import com.radio.cast.chat.repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final ChatMessageRepository chatMessageRepository;

  @Transactional
  public ChatMessageDto saveMessage(Long chatChanneId, ChatMessageDto chatDto){
    Chat chatEntity = new Chat(chatChanneId, chatDto.getSender(), chatDto.getContent());
    chatMessageRepository.save(chatEntity);
    return chatDto;
  }
}
