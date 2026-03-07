package com.radio.cast.chat.service;

import java.util.ArrayList;
import java.util.List;

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

  /**
   * 사용자가 작성한 채팅 저장
   * @param radioChannelId
   * @param chatDto
   * @return
   */
  @Transactional
  public ChatMessageDto saveMessage(Long radioChannelId, ChatMessageDto chatDto){
    Chat chatEntity = new Chat(radioChannelId, chatDto.getSender(), chatDto.getContent());
    chatMessageRepository.save(chatEntity);
    return chatDto;
  }

  /**
   * 특정 채팅방 상위 20개 채팅 조회
   * @param radioChannelId
   * @return
   */
  public List<ChatMessageDto> chatHistory(Long radioChannelId){
    List<ChatMessageDto> chatHistory = new ArrayList<>();
    for(Chat chat : chatMessageRepository.findTop20ByRadioChannelIdOrderByCreatedAtDesc(radioChannelId)){
      chatHistory.add(new ChatMessageDto(chat));
    }
    return chatHistory;
  }
}
