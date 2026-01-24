package com.radio.cast.radioCast.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radio.cast.radioCast.entity.RadioChannel;
import com.radio.cast.radioCast.service.RadioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/radio")
public class RadioController {
  private final RadioService radioService;
  
  @GetMapping("/list")
  public ResponseEntity<List<RadioChannel>> radioChannelList(){
    return ResponseEntity.ok(radioService.RadioChannelList());
  }
}
