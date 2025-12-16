package com.radio.cast.basicFunction.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.radio.cast.basicFunction.user.dto.SignUpRequest;
import com.radio.cast.basicFunction.user.dto.SignUpResponse;
import com.radio.cast.basicFunction.user.entity.User;
import com.radio.cast.basicFunction.user.repository.UserRepository;
import com.radio.cast.globalFile.exception.BusinessException;
import com.radio.cast.globalFile.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
  
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  /**
   * 아이디 및 중복 체크 메소드
   * @param signUpRequest
   */
  private void checkDuplicateUser(SignUpRequest signUpRequest){
    if(userRepository.existsByUsername(signUpRequest.getUsername())){
      throw new BusinessException(ErrorCode.DUPLICATE_USERNAME);
    }

    if(userRepository.existsByEmail(signUpRequest.getEmail())){
      throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
    }
  }

  /**
   * 회원가입 Service
   * @param signUpDto
   * @return
   */
  public SignUpResponse signUp(SignUpRequest signUpDto){
    //중복확인 먼저 진행
    checkDuplicateUser(signUpDto);

    //비밀번호 암호화 후 회원데이터 저장
    signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
    User user = userRepository.save(signUpDto.toEntity());
    return new SignUpResponse(user);
  }
}
