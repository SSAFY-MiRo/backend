package com.ssafy.miro.auth.presentation;

import com.ssafy.miro.auth.presentation.request.TokenCodeRequest;
import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.code.SuccessCode;
import com.ssafy.miro.common.jwt.JwtProvider;
import com.ssafy.miro.auth.application.UserOAuthService;
import com.ssafy.miro.auth.application.response.UserTokenResponse;
import com.ssafy.miro.auth.domain.dto.UserToken;
import com.ssafy.miro.common.redis.RedisTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserOAuthController {
    private final UserOAuthService userOAuthService;
    private final JwtProvider jwtProvider;
//
//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse<String>> login() {
//        return ResponseEntity.ok().body(ApiResponse.of(SuccessCode.OK, userOAuthService.getUrl()));
//    }

    @PostMapping("/login/oauth2/code/google")
    public ResponseEntity<ApiResponse<UserTokenResponse>> oauth2Login(@RequestBody TokenCodeRequest tokenCodeRequest, HttpServletResponse response) {
        UserToken userToken = userOAuthService.authenticateUser(tokenCodeRequest.code());
        return jwtProvider.sendToken(
                response,
                userToken.getAccessToken(),
                userToken.getRefreshToken());
    }

}