package com.radio.cast.radioCast.service;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.radio.cast.globalFile.config.Mp3Util;
import com.radio.cast.globalFile.dto.Mp3SaveResponse;
import com.radio.cast.radioCast.dto.RadioChannelCreateRequest;
import com.radio.cast.radioCast.dto.RadioTrackResponse;
import com.radio.cast.radioCast.entity.RadioChannel;
import com.radio.cast.radioCast.entity.RadioPlayList;
import com.radio.cast.radioCast.entity.RadioTrack;
import com.radio.cast.radioCast.repository.RadioChannelRepository;
import com.radio.cast.radioCast.repository.RadioPlaylistRepository;
import com.radio.cast.radioCast.repository.RadioTrackRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RadioService {
  private final RadioChannelRepository radioChannelRepository;
  private final RadioTrackRepository radioTrackRepository;
  private final RadioPlaylistRepository radioPlaylistRepository;
  private final Mp3Util mp3Util;

  /**
   * 라디오 채널 전체 조회 서비스
   * @return
   */
  public List<RadioChannel> RadioChannelList(){
    return radioChannelRepository.findAll();
  }

  /**
   * 채널 ID로 특정 라디오 채널 정보 조회 서비스
   * @param radioChannelId
   * @return
   */
  public RadioChannel radioChannel(Long radioChannelId){
    return radioChannelRepository.findByRadioChannelId(radioChannelId);
  }

  /**
   * 채널 소유 유저 ID로 라디오 체널 정보 조회 서비스
   * @param radioUserId
   * @return
   */
  public RadioChannel radioChannelUser(Long radioUserId){
    return radioChannelRepository.findByRadioUserId(radioUserId).get();
  }

  /**
   * 해당 라디오 현재 트랙 조회 서비스
   * @param playlistId
   * @return
   */
  public List<RadioTrack> RadioTrackList(Long playlistId){
    return radioTrackRepository.findByRadioPlaylistIdOrderByTrackOrder(playlistId);
  }

  /**
   * 트랙 정보 조회 서비스
   * @param radioTrackId
   * @return
   */
  public RadioTrack trackData(Long radioTrackId){
    return radioTrackRepository.findByRadioTrackId(radioTrackId).get();
  }

  /**
   * 현재 재생중인 채널의 트랙 조회 서비스
   * @param radioChannelId
   * @return
   */
  public RadioTrackResponse NowPlaying(Long radioChannelId) {
    RadioChannel channel = radioChannelRepository.findByRadioChannelId(radioChannelId);
    RadioPlayList playlist = radioPlaylistRepository.findByRadioChannelId(channel);
    //라디오 채널 아이디 -> 플레이 리스트 조회 후 플레이 리스트 아이디 추출
    List<RadioTrack> tracks =
      // radioTrackRepository.findByPlaylistIdOrderByTrackOrder(channel.getPlaylistId());
      radioTrackRepository.findByRadioPlaylistIdOrderByTrackOrder(playlist.getRadioPlaylistId());

    //해당 채널 실행 시간
    long elapsed =
        Duration.between(
            channel.getStartTime(),
            LocalDateTime.now()
        ).getSeconds();

    //해당 채널 플레이 리스트 총 시간 계산
    long totalDuration = tracks.stream().mapToLong(RadioTrack::getDuration).sum();

    //해당 채널 접속시 재생중인 음원 시간 계산(해당 채널 실행 시간 % 플레이리스트 총 시간)
    long playTime = elapsed % totalDuration;

    long acc = 0;
    for (RadioTrack track : tracks) {
        acc += track.getDuration();
        if (playTime < acc) {
            long offset = playTime - (acc - track.getDuration());

            return new RadioTrackResponse(
                channel.getRadioChannelId(),
                track.getRadioTrackId(),
                track.getRadioTrackTitle(),
                track.getArtist(),
                // "http://localhost:8081/radio/tracks/" + track.getRadioTrackId(),
                track.getAudioUrl(),
                offset
            );
        }
    }

    throw new IllegalStateException("음원이 없습니다");
  }

  /**
   * 해당 채널 시작으로 변경 서비스
   * @param radioUserId
   */
  @Transactional
  public void startRadioChannel(Long radioChannelId, Long id){
    RadioChannel channel = radioChannelRepository.findByRadioChannelId(radioChannelId);

    //내 채널인지 체크
    if (!channel.getRadioUserId().equals(id)) {
        throw new AccessDeniedException("내 채널 아님");
    }

    channel.setStatus(true);
    channel.setStartTime(LocalDateTime.now());
  }

  /**
   * 해당 채널 방송 준비로 변경 서비스
   * @param radioUserId
   */
  @Transactional
  public void stopRadioChannel(Long radioChannelId, Long id){
    RadioChannel channel = radioChannelRepository.findByRadioChannelId(radioChannelId);

    //내 채널인지 체크
    if (!channel.getRadioUserId().equals(id)) {
        throw new AccessDeniedException("내 채널 아님");
    }

    channel.setStatus(false);
  }

  @Transactional
  public void createChannelWithTracks(RadioChannelCreateRequest data) throws IOException {

    //채널 생성 (먼저 만들어야 channelId 사용 가능)
    RadioChannel channel = radioChannelRepository.save(
      RadioChannel.builder()
      .radioChannelName(data.getRadioChannelName())
      .radioUserId(data.getRadioUserId())
      .description(data.getDescription())
      // .playlistId(playlist.getRadioPlaylistId())
      .startTime(LocalDateTime.now())
      .status(false)
      .build()
    );

    RadioPlayList playlistSetup = new RadioPlayList();
    playlistSetup.setRadioPlaylistName(data.getRadioChannelName() + " Playlist");
    playlistSetup.setRadioChannelId(channel);
    // 플레이리스트 생성
    RadioPlayList playlist = radioPlaylistRepository.save(playlistSetup);

    int order = 1;

    //트랙 저장
    for (MultipartFile file : data.getTracks()) {
      // String audioUrl = mp3Util.saveMp3AndGetPath(file, channel.getRadioChannelId());
      // File realFile = new File("src/main/resources/static" + audioUrl);
      Mp3SaveResponse result = mp3Util.saveMp3AndGetPath(file, channel.getRadioChannelId());
      
      long duration = mp3Util.getDurationInSeconds(result.getFile());
      // System.out.println("시간 추출 결과 : " + duration);

      //파일명에서 제목, 가수명 분리
      //확장자 제거 (예: "Song - Artist.mp3" -> "Song - Artist")
      String title = "";
      String artist = "";
      String originalFilename = file.getOriginalFilename();
      String fileNameWithoutExt = originalFilename.substring(0, originalFilename.lastIndexOf("."));

      // 하이픈(-)을 기준으로 분할
      String[] parts = fileNameWithoutExt.split("-");

      if (parts.length >= 2) {
          title = parts[0].trim();
          artist = parts[1].trim(); // 하이픈이 여러 개라면 첫 번째와 두 번째만 사용
      } else {
          // 하이픈이 없는 경우에 대한 예외 처리
          title = fileNameWithoutExt.trim();
          artist = "Unknown";
      }

      RadioTrack track = RadioTrack.builder()
          .radioPlaylist(playlist)
          .radioTrackTitle(title)
          .artist(artist)
          .duration(duration)
          .audioUrl(result.getAudioUrl())
          .trackOrder(order++)
          .build();

      radioTrackRepository.save(track);
    }
  }

}
