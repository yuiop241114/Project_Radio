package com.radio.cast.radioCast.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.radio.cast.radioCast.entity.RadioTrack;

public interface RadioTrackRepository extends JpaRepository<RadioTrack, Long>{

  //라디오, 플레이리스트, 트랙 외래키 제약조건 추가로 인한 변경 
  // List<RadioTrack> findByRadioPlaylistIdOrderByTrackOrder(Long radioPlaylistId);
  @Query("SELECT t FROM RadioTrack t WHERE t.radioPlaylist.radioPlaylistId = :playlistId ORDER BY t.trackOrder")
  List<RadioTrack> findByRadioPlaylistIdOrderByTrackOrder(Long playlistId);

  Optional<RadioTrack> findByRadioTrackId(Long radioTrackId);

}
