package com.radio.cast.radioCast.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.radio.cast.radioCast.entity.RadioTrack;

public interface RadioTrackRepository extends JpaRepository<RadioTrack, Long>{
  List<RadioTrack> findByPlaylistIdOrderByTrackOrder(Long playlistId);
}
