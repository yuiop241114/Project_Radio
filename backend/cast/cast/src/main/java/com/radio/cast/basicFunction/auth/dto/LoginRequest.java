package com.radio.cast.basicFunction.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
  private String uesrname;
  private String password;
}
