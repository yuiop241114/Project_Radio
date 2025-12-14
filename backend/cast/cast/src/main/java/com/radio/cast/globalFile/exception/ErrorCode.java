package com.radio.cast.globalFile.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  //private final HttpStatus status;
  //private final String message;

  //회원가입 관련
  DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),
  DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    
}
