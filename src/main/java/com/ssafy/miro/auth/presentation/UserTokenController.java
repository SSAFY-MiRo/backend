package com.ssafy.miro.auth.presentation;

import com.ssafy.miro.auth.application.response.UserTokenResponse;
import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.auth.Auth;
import com.ssafy.miro.common.jwt.JwtProvider;
import com.ssafy.miro.common.redis.RedisTokenService;
import com.ssafy.miro.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class UserTokenController {
    private final JwtProvider jwtProvider;
    private final RedisTokenService redisTokenService;

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<UserTokenResponse>> refreshToken(@CookieValue("refresh-token") String refreshToken) {

        jwtProvider.validateTokens(null, refreshToken);
        Long userId = jwtProvider.getId(refreshToken);

        String storedRefreshToken = redisTokenService.getToken(userId.toString());
        if (!refreshToken.equals(storedRefreshToken)) {
            log.warn("Redis에 저장된 토큰과 일치하지 않음: userId={}, refreshToken={}", userId, refreshToken);
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        String newAccessToken = jwtProvider.regenerateAccessToken(userId);

        return ResponseEntity.ok().body(ApiResponse.onSuccess(new UserTokenResponse(newAccessToken)));
    }

    @GetMapping("/test")
    public void testet(@Auth User user) {
        log.info("user_name = {}, user_email = {}", user.getNickname(), user.getEmail());
    }

    @DeleteMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@CookieValue ("refresh-token") String refreshToken) {
        redisTokenService.deleteToken(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
