package com.radio.cast.radioCast.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radio.cast.radioCast.entity.RadioChannel;
import com.radio.cast.radioCast.entity.RadioTrack;
import com.radio.cast.radioCast.service.RadioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/radio")
public class RadioController {
  private final RadioService radioService;
  
  /**
   * 라디오 채널 전체 조회 컨트롤러
   * @return
   */
  @GetMapping("/list")
  public ResponseEntity<List<RadioChannel>> radioChannelList(){
    return ResponseEntity.ok(radioService.RadioChannelList());
  }

  /**
   * 해당 라디오 플레이 리스트 조회 컨트롤러
   * @param playlistId
   * @return
   */
  @GetMapping("/playlist/{playlistId}")
  public ResponseEntity<List<RadioTrack>> radioTrackList(@PathVariable Long playlistId){
    return ResponseEntity.ok(radioService.RadioTrackList(playlistId));
  }
}
