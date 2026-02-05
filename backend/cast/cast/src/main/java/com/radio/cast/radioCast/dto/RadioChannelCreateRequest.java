package com.radio.cast.radioCast.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RadioChannelCreateRequest {
  private String radioChannelName;

  private Long radioUserId;

  private String description;

  private List<MultipartFile> tracks;
}
