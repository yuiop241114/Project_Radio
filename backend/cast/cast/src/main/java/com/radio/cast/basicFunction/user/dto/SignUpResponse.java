package com.radio.cast.basicFunction.user.dto;

import com.radio.cast.basicFunction.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse {
  private Long id;
  private String username;
  private String email;

  /**
   * 비밀번호를 제외한 응답 User 데이터
   * @param user
   */
  public SignUpResponse(User user){
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
  }
}
