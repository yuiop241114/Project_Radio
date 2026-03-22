package com.radio.cast.radioCast.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "radio_channel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RadioChannel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long radioChannelId;

  private String radioChannelName;

  private Long radioUserId;

  private String description;

  @Column(name = "start_time")
  private LocalDateTime startTime;

  // @Column(name = "playlist_id")
  // private Long playlistId;

  private boolean status;

  public RadioChannel orElseThrow(Object object) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
  }
}
