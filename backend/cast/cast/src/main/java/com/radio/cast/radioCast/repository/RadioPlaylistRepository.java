package com.radio.cast.radioCast.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.radio.cast.radioCast.entity.RadioChannel;
import com.radio.cast.radioCast.entity.RadioPlayList;

public interface RadioPlaylistRepository extends JpaRepository<RadioPlayList, Long>{
  RadioPlayList findByRadioChannelId(RadioChannel RadioChannelId);
}
