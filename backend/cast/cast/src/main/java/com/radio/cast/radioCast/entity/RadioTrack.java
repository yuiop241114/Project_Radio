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
@Table(name = "radio_track")
@Getter
@NoArgsConstructor
public class RadioTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    @Column(name = "radio_track_id") 
    private Long radioTrackId;

    @Column(name = "playlist_id")
    private Long playlistId;

    @Column(name = "radio_track_name")
    private String title;

    private String artist;

    private int duration;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "track_order")
    private int trackOrder;
}
