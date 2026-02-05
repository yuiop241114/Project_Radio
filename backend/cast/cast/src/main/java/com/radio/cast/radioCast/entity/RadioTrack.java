package com.radio.cast.radioCast.entity;

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

@Entity
@Table(name = "radio_track")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RadioTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    @Column(name = "radio_track_id") 
    private Long radioTrackId;

    @Column(name = "playlist_id")
    private Long playlistId;

    @Column(name = "radio_track_title")
    private String radioTrackTitle;

    private String artist;

    private Long duration;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "track_order")
    private int trackOrder;
}
