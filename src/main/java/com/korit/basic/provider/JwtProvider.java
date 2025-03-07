package com.korit.basic.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// JWT:
// - Json Web Token, RFC7519 표준에 정의된 JSON 형식의 문자열을 포함하는 토큰
// - 암호화가 되어 있어 클라이언트와 서버 간에 안전한 데이터 전송을 수행할 수 있음
// - 헤더: 토큰 유형, 서명에 사용된 암호화 알고리즘이 지정되어 있음
// - 페이로드: 클라이언트 혹은 서버가 전달할 데이터가 포함되어 있음
// - 서명: 헤더와 페이로드를 인코딩하여 합치고 비밀키로 암호화한 데이터
@Component
public class JwtProvider {

    // JWT 암호화에 사용되는 비밀키는 보안 관리되어야 함
    // 코드상에 직접 작성하는 것은 보안상 좋지 않음

    // 해결책
    // 1. application.properties / application.yaml에 등록
    // - application.properties / application.yaml에 비밀키를 작성
    // - @Value() 어노테이션을 사용하여 값을 가져옴
    // - 주의사항 : application.properties / application.yaml을 .gitignore에 등록해야함
    @Value("${jwt.key}")
    private String secretKey;

    // 2. 시스템의 환경변수로 등록하여 사용
    // - OS 자체의 시스템 환경변수에 비밀키를 등록
    // - Spring에서 시스템 환경변수를 읽어서 사용

    // 3. 외부 데이터 관리 도구를 사용
    // - 자체 서버가 아닌 타 서버에 등록된 Vault 도구를 사용하여 비밀키 관리
    // - OS 부팅시에 Vault 서버에서 비밀키를 가져와 사용
    // - OS 부팅시 마다 새로운 비밀키를 자동으로 부여함

    // JWT 생성 메서드
    public String create(String name) {

        // 비밀키 객체 생성
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // JWT 만료 시간
        Date expiration = Date.from(Instant.now().plus(9, ChronoUnit.HOURS));

        // JWT 생성
        String jwt = Jwts.builder()
            // 서명 알고리즘과 서명에 사용할 키 지정
            .signWith(key, SignatureAlgorithm.HS256)
            // 페이로드
            // 작성자
            .setSubject(name)
            // 생성시간 (현재시간)    
            .setIssuedAt(new Date())
            // 만료시간
            .setExpiration(expiration)
            // 인코딩 (압축)
            .compact();

        return jwt;

    }

    // JWT 검증 메서드
    public String validate(String jwt) {

        // JWT 검증 결과로 반환받는 페이로드가 저장될 변수 
        Claims claims = null;

        // 비밀키 객체 생성
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        
        try {

            claims = Jwts .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

        return claims.getSubject();

    }

}