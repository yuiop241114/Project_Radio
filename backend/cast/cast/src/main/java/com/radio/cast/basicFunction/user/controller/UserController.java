package com.radio.cast.basicFunction.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    
  /**
   * 회원가입 컨트롤러
   * @param entity
   * @return
   */
  @PostMapping("/SignUp")
  public String postMethodName(@RequestBody String entity) {
      
      return entity;
  }
  
}
