package com.radio.cast.radioCast.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.radio.cast.radioCast.dto.RadioChannelCreateRequest;
import com.radio.cast.radioCast.dto.RadioTrackResponse;
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
   * 채널 ID로 특정 라디오 채널 정보 조회 컨트롤러
   * @param radioChannelId
   * @return
   */
  @GetMapping("/detail")
  public ResponseEntity<RadioChannel> radioChannel(@RequestParam Long radioChannelId){
    return ResponseEntity.ok(radioService.radioChannel(radioChannelId));
  }

  @GetMapping("/detail/user")
  public ResponseEntity<RadioChannel> radioChannelUser(@RequestParam Long radioUserId){
    return ResponseEntity.ok(radioService.radioChannelUser(radioUserId));
  }

  /**
   * 해당 라디오 플레이 리스트 조회 컨트롤러
   * @param playlistId
   * @return
   */
  @GetMapping("/playlist")
  public ResponseEntity<List<RadioTrack>> radioTrackList(@RequestParam Long playlistId){
    return ResponseEntity.ok(radioService.RadioTrackList(playlistId));
  }

  /**
   * 현재 채널의 재생 트랙 조회 컨트롤러
   * @param radioChannelId
   * @return
   */
  @GetMapping("/now")
  public ResponseEntity<RadioTrackResponse> nowPlaying(@RequestParam Long radioChannelId){
    return ResponseEntity.ok(radioService.NowPlaying(radioChannelId));
  }

  /**
   * 채널 시작 컨트롤러
   * @param radioChannelId
   * @param id
   * @return
   */
  @PostMapping("/channel/start/{radioChannelId}/{id}")
  public ResponseEntity<String> startRadioChannel(
    @PathVariable Long radioChannelId,
    @PathVariable Long id){
    radioService.startRadioChannel(radioChannelId, id);
    return ResponseEntity.ok("on");
  }

  /**
   * 채널 종료 컨트롤러
   * @param radioChannelId
   * @param id
   * @return
   */
  @PostMapping("/channel/stop/{radioChannelId}/{id}")
  public ResponseEntity<String> stopRadioChannel(
    @PathVariable Long radioChannelId,
    @PathVariable Long id){
    radioService.stopRadioChannel(radioChannelId, id);
    return ResponseEntity.ok("off");
  }

  /**
   * 해당 트랙 mp3 파일 전달 컨트롤러(Range를 사용해서 코드 다시 구성해야함)
   * @param radioTrackId
   * @return
   */
  @GetMapping(value = "/tracks/{radioTrackId}", produces = "audio/mpeg")
  public ResponseEntity<Resource> streamTrack(@PathVariable Long radioTrackId) {
    String filePath = "C:/Project_Radio" + radioService.trackData(radioTrackId).getAudioUrl();

    File file = new File(filePath);
    if (!file.exists()) {
        return ResponseEntity.notFound().build();
    }

    Resource resource = new FileSystemResource(file);

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
            .header(HttpHeaders.ACCEPT_RANGES, "bytes")
            .contentLength(file.length())
            .body(resource);
  }

  /**
   * 라디오 채널 생성 컨트롤러
   * @param radioChannelCreateRequest
   * @return
   * @throws IOException
   */
  @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> radioChannelCreate(@ModelAttribute RadioChannelCreateRequest radioChannelCreateRequest) throws IOException{
    radioService.createChannelWithTracks(radioChannelCreateRequest);
    return ResponseEntity.ok("success");
  }

}
