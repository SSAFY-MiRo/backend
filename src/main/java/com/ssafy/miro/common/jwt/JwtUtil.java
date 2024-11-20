package com.ssafy.miro.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parser;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtil {

    private SecretKey secretKey;
    private int expirationTimeMillis = 864000000;
    private String tokenPrefix = "Bearer ";
    private ObjectMapper objectMapper = new ObjectMapper();

    public JwtUtil(@Value("${jwt.secret}") SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public boolean isIncludeTokenPrefix(String header) {
        return header.startsWith(tokenPrefix.trim());
    }

    // email 추출
    public String getUsername(String token) {
        return parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    // 권한 추출
    public String getRole(String token) {
        return parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    // token 유효확인
    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    // accessToken인지 refreshToken인지 확인
    public String getCategory(String token) {
        return parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    // JWT 발급
    public String createToken(String email, String userType, Long expiredMs) {
        return Jwts.builder()
                .claim("email", email)
                .claim("role", userType)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey).compact();
    }
}
