package com.ssafy.miro.common.jwt;


import com.ssafy.miro.auth.application.response.UserTokenResponse;
import com.ssafy.miro.auth.domain.dto.UserToken;
import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.code.SuccessCode;
import com.ssafy.miro.common.exception.GlobalException;
import com.ssafy.miro.common.redis.RedisTokenService;
import com.ssafy.miro.user.application.response.UserInfo;
import com.ssafy.miro.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

import static com.ssafy.miro.common.code.ErrorCode.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret}")
    private String key;
    @Value("${jwt.expired-time}")
    private long expiredTime;
    private SecretKey secretKey;
    private static final String REFRESH_TOKEN = "refresh-token";

    private final RedisTokenService redisTokenService;
    private final BearerAuthorizationExtractor bearerAuthorizationExtractor;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public UserToken generateAuthToken(Long id, User user) {
        String accessToken = createToken(id);
        String refreshToken = createToken(id);
        redisTokenService.saveToken(refreshToken, String.valueOf(id), 3600);
        return new UserToken(id, accessToken, refreshToken, UserInfo.of(user));
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
            String userId = redisTokenService.getToken(refreshToken);
            if (userId == null) throw new GlobalException(INVALID_REFRESH_TOKEN);
        } catch (JwtException e) {
            throw new GlobalException(INVALID_REFRESH_TOKEN);
        }

    }

    private void validateAccessToken(String token) {
        try {
            parseToken(token);
        } catch (JwtException e) {
            throw new GlobalException(INVALID_ACCESS_TOKEN);
        }
    }

    private Jws<Claims> parseToken(String token) {
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        } catch (UnsupportedJwtException e) {
            throw new GlobalException(INVALID_JWT_FORMAT);
        }  catch (IllegalArgumentException e) {
            throw new GlobalException(JWT_NOT_FOUND);
        }
    }

    private String createToken(Long id) {
        return Jwts.builder()
                .claim("id", id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(secretKey).compact();
    }

    public ResponseEntity<ApiResponse<UserTokenResponse>> sendToken(HttpServletResponse response, String accessToken, String refreshToken, UserInfo userInfo) {
        Cookie cookie = new Cookie("refresh-token", refreshToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        log.info("토큰 전달됨 = {}", accessToken);
        return ResponseEntity.ok(ApiResponse.of(SuccessCode.AUTH_SUCCESS, new UserTokenResponse(accessToken, userInfo)));
    }

    public String regenerateAccessToken(Long id) {
        return createToken(id);
    }

    public Long validateAndExtractUserId(HttpServletRequest request, NativeWebRequest webRequest) {

        String refreshToken = extractRefreshToken(request.getCookies());
        String accessToken = bearerAuthorizationExtractor.extractAccessToken(webRequest.getHeader(HttpHeaders.AUTHORIZATION));

        // 유효성 검사
        validateTokens(accessToken, refreshToken);

        // 사용자 ID 추출
        return getId(accessToken);
    }


    private String extractRefreshToken(Cookie... cookies) {
        if (cookies == null) throw new GlobalException(INVALID_REFRESH_TOKEN);

        return Arrays.stream(cookies)
                .filter(this::isValidRefreshToken)
                .findFirst()
                .orElseThrow(() -> new GlobalException(JWT_NOT_FOUND))
                .getValue();
    }

    private boolean isValidRefreshToken(Cookie cookie) {
        return cookie.getName().equals(REFRESH_TOKEN);
    }
}
