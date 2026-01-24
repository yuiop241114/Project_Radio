package com.radio.cast.radioCast.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.radio.cast.radioCast.entity.RadioChannel;
import com.radio.cast.radioCast.repository.RadioChannelRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RadioService {
  private final RadioChannelRepository radioChannelRepository;

  public List<RadioChannel> RadioChannelList(){
    return radioChannelRepository.findAll();
  }
}
