package com.radio.cast.radioCast.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.radio.cast.radioCast.entity.RadioChannel;
import com.radio.cast.radioCast.entity.RadioTrack;
import com.radio.cast.radioCast.repository.RadioChannelRepository;
import com.radio.cast.radioCast.repository.RadioTrackRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RadioService {
  private final RadioChannelRepository radioChannelRepository;
  private final RadioTrackRepository radioTrackRepository;

  /**
   * 라디오 채널 전체 조회 서비스
   * @return
   */
  public List<RadioChannel> RadioChannelList(){
    return radioChannelRepository.findAll();
  }

  /**
   * 해당 라디오 채널 플레이 리스트 조회 서비스
   * @param playlistId
   * @return
   */
  public List<RadioTrack> RadioTrackList(Long playlistId){
    return radioTrackRepository.findByPlaylistIdOrderByTrackOrder(playlistId);
  }
}
