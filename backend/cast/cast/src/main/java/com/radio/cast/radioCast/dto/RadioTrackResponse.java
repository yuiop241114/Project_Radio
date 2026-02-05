package com.radio.cast.radioCast.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RadioTrackResponse {
  private Long radioChannelId;

  private Long radioTrackId;

  private String radioTrackTitle;

  private String artist;

  private String audioUrl;

  private Long offset;
}
