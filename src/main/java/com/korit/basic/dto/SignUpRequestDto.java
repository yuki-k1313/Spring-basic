package com.korit.basic.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    // 정규식 : 
    // - ^ : 문자열의 시작
    // - [a-zA-Z0-9]: 영문 대소문자와 숫자만 허용
    // - {1,20}: 길이가 최소 1이상, 최대 20이하
    // - $ : 문자열의 끝
    @Pattern(regexp="^[a-zA-Z0-9]{1,20}$")
    private String userId;

    @NotBlank
    // 정규식 :
    // - ^ : 문자열의 시작
    // - (?=.*[a-z]) : 최소 하나 이상의 영소문자 포함
    // - (?=.*[A-Z]) : 최소 하나 이상의 영대문자 포함
    // - (?=.*[0-9]) : 최소 하나 이상의 숫자 포함
    // - (?=.*[!@#$%^&*-_?]) : 최소 하나 이상의 특수문자 포함
    // - [a-zA-Z0-9!@#$%&*-_?]{8,20} : 영대소문자 + 숫자 + 특수문자만 구성가능하며 최소 8자 이상 최대 20자 이하
    // - $ : 문자열의 끝
    @Pattern
    (regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*-_?])[a-zA-Z0-9!@#$%&*-_?]{8,20}$")
    private String userPassword;

    @NotBlank
    @Length(max=4)
    private String userName;

    @NotBlank
    private String userAddress;

    @NotBlank
    // 정규식:
    // - ^ : 문자열의 시작
    // - 010- : 반드시 해당 부분은 010- 이어야 함
    // - \\d{3,4} : 최소 3자, 최대 4자의 숫자
    // - - : 반드시 해당 부분은 - 이어야 함
    // - \\d{4} : 4자리 숫자
    // - $ : 문자열의 끝
    @Pattern(regexp="^010-\\d{3,4}-\\d{4}$")
    private String userTelNumber;  

}