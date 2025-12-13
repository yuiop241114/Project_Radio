package com.radio.cast.basicFunction.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class LoginRequest {
  private String uesrname;
  private String password;
}
