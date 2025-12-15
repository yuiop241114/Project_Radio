package com.radio.cast.basicFunction.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radio.cast.basicFunction.user.dto.SignUpRequest;
import com.radio.cast.basicFunction.user.dto.SignUpResponse;
import com.radio.cast.basicFunction.user.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

  private UserService userService;

  /**
   * 회원가입 컨트롤러
   * @param entity
   * @return
   */
  @PostMapping("/SignUp")
  public ResponseEntity<SignUpResponse> postMethodName(@RequestBody SignUpRequest signUpRequest) {
      SignUpResponse signUpResponse = userService.signUp(signUpRequest);
      return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
  }
  
}
