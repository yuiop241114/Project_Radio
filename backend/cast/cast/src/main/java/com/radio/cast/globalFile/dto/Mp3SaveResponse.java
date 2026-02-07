package com.radio.cast.globalFile.dto;

import java.io.File;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Mp3SaveResponse {
  private String audioUrl;
  private File file;
}
