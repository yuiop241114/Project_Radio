package com.radio.cast.radioCast.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "radio_playlist")
@Getter
@NoArgsConstructor
public class RadioPlayList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "radio_playlist_id")
  private Long radioPlaylistId;

  @Column(name = "radio_playlist_name")
  private String radioPlaylistName;
}
