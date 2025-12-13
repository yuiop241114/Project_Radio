package com.radio.cast.basicFunction.user.dto;

import com.radio.cast.basicFunction.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequest {
  private String username;
  private String password;
  private String email;

  public User toEntity(){
    return User.builder()
               .username(this.username)
               .password(this.password)
               .email(this.email)
               .build();
  }

}
