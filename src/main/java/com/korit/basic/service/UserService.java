package com.korit.basic.service;

import org.springframework.http.ResponseEntity;

import com.korit.basic.dto.PostUserRequestDto;
import com.korit.basic.dto.ResponseDto;

public interface UserService {

    ResponseEntity<ResponseDto> postUser(PostUserRequestDto dto);

}
