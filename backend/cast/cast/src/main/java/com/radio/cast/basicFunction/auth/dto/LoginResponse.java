package com.radio.cast.basicFunction.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
  private String accessToken;
  private String RefreshToken;
  private Long id;
  private String email;
  private String username;
}
