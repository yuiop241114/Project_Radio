package com.radio.cast.radioCast.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "radio_playlist")
@Getter
@Setter
@NoArgsConstructor
public class RadioPlayList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "radio_playlist_id")
  private Long radioPlaylistId;

  @Column(name = "radio_playlist_name")
  private String radioPlaylistName;

  //외래키 설정
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "radio_channel_id", nullable = false)
  private RadioChannel radioChannelId;
}
