package com.radio.cast.basicFunction.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radio.cast.basicFunction.auth.dto.LoginRequest;
import com.radio.cast.basicFunction.auth.dto.LoginResponse;
import com.radio.cast.basicFunction.auth.service.AuthService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
    String token = authService.login(loginRequest);
    return ResponseEntity.ok(new LoginResponse(token));
  }
}
