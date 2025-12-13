package com.radio.cast.basicFunction.user.service;

import com.radio.cast.basicFunction.user.dto.SignUpRequest;
import com.radio.cast.basicFunction.user.dto.SignUpResponse;
import com.radio.cast.basicFunction.user.entity.User;
import com.radio.cast.basicFunction.user.repository.UserRepository;

public class UserService {
  
private UserRepository userRepository;

  /**
   * 회원가입 Service
   * @param signUpDto
   * @return
   */
  public SignUpResponse SignUp(SignUpRequest signUpDto){
    User user = userRepository.save(signUpDto.toEntity());
    return new SignUpResponse(user);
  }
}
