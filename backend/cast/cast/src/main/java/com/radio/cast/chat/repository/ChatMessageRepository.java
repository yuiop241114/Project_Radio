package com.radio.cast.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radio.cast.chat.entity.Chat;

@Repository
public interface ChatMessageRepository extends JpaRepository<Chat, Long>{
  List<Chat> findTop20ByRadioChannelIdOrderByCreatedAtDesc(Long radioChannelId);
}
