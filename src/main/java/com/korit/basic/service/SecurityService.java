package com.korit.basic.service;

import org.springframework.http.ResponseEntity;

import com.korit.basic.dto.SignInRequestDto;
import com.korit.basic.dto.SignUpRequestDto;

public interface SecurityService {
    ResponseEntity<String> signUp(SignUpRequestDto dto);
    ResponseEntity<String> signIn(SignInRequestDto dto);
}
