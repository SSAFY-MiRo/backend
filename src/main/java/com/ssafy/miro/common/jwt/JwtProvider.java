package com.ssafy.miro.common.jwt;


import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.code.SuccessCode;
import com.ssafy.miro.user.application.response.UserTokenResponse;
import com.ssafy.miro.user.domain.dto.UserToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import static io.jsonwebtoken.Jwts.parser;

@Slf4j
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtProvider {
    @Value("${jwt.secret}")
    private String key;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        log.info("JWT provider initialized");
        log.info("JWT key: {}", key);
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public UserToken generateAuthToken(Long id) {
        String accessToken = createToken(id);
        String refreshToken = createToken(id);
        return new UserToken(accessToken, refreshToken);
    }

    public void validateTokens(String accessToken, String refreshToken) {
        validateAccessToken(accessToken);
        validateRefreshToken(refreshToken);
    }

    public Long getId(String token) {
        return parseToken(token).getPayload().get("id", Long.class);
    }

    private void validateRefreshToken(String refreshToken) {
        try {
            parseToken(refreshToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateAccessToken(String token) {
        try {
            parseToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
    }

    public String regenerateAccessToken(Long id) {
        return createToken(id);
    }

    private String createToken(Long id) {
        System.out.println(secretKey);
        return Jwts.builder()
                .claim("id", id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 864000000))
                .signWith(secretKey).compact();

    }

    public ResponseEntity<ApiResponse<UserTokenResponse>> sendToken(HttpServletResponse response, String accessToken, String refreshToken) {
        Cookie cookie = new Cookie("refresh-token", refreshToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        return ResponseEntity.ok(ApiResponse.of(SuccessCode.CREATE_PLAN, new UserTokenResponse("Bearer " + accessToken)));
    }
}
