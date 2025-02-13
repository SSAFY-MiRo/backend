package com.ssafy.miro.auth.presentation;

import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.jwt.JwtProvider;
import com.ssafy.miro.auth.application.UserOAuthService;
import com.ssafy.miro.auth.application.response.UserTokenResponse;
import com.ssafy.miro.auth.domain.dto.UserToken;
import com.ssafy.miro.common.redis.RedisTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserOAuthController {
    private final UserOAuthService userOAuthService;
    private final JwtProvider jwtProvider;

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect(userOAuthService.getUrl());
    }

    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<ApiResponse<UserTokenResponse>> oauth2Login(String code, HttpServletResponse response) {
        UserToken userToken = userOAuthService.authenticateUser(code);
        return jwtProvider.sendToken(
                response,
                userToken.getAccessToken(),
                userToken.getRefreshToken());
    }

}