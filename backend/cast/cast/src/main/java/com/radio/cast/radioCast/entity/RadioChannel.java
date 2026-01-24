package com.radio.cast.radioCast.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "radio_channel")
@Getter
@NoArgsConstructor
public class RadioChannel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long radioChannelId;

  private String radioChannelName;

  private String description;

  @Column(name = "start_time")
  private LocalDateTime startTime;

  @Column(name = "playlist_id")
  private Long playlistId;
}
