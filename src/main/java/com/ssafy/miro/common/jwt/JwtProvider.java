package com.ssafy.miro.common.jwt;


import com.ssafy.miro.auth.exception.InvalidJwtException;
import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.code.SuccessCode;
import com.ssafy.miro.auth.application.response.UserTokenResponse;
import com.ssafy.miro.auth.domain.dto.UserToken;
import com.ssafy.miro.common.redis.RedisTokenService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.ssafy.miro.common.code.ErrorCode.*;

@Slf4j
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtProvider {
    @Value("${jwt.secret}")
    private String key;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public UserToken generateAuthToken(Long id) {
        String accessToken = createToken(id);
        String refreshToken = createToken(id);
        return new UserToken(id, accessToken, refreshToken);
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
            throw new InvalidJwtException(INVALID_REFRESH_TOKEN);
        }
    }

    private void validateAccessToken(String token) {
        try {
            parseToken(token);
        } catch (Exception e) {
            throw new InvalidJwtException(INVALID_ACCESS_TOKEN);
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

    public ResponseEntity<ApiResponse<UserTokenResponse>> sendToken(HttpServletResponse response, Long userId, String accessToken, String refreshToken) {
        Cookie cookie = new Cookie("refresh-token", refreshToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        return ResponseEntity.ok(ApiResponse.of(SuccessCode.CREATE_PLAN, new UserTokenResponse(accessToken, userId)));
    }
}
