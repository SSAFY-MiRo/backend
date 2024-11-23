package com.ssafy.miro.auth.presentation;

import com.ssafy.miro.auth.presentation.request.TokenCodeRequest;
import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.auth.Auth;
import com.ssafy.miro.common.code.SuccessCode;
import com.ssafy.miro.common.jwt.JwtProvider;
import com.ssafy.miro.auth.application.UserOAuthService;
import com.ssafy.miro.auth.application.response.UserTokenResponse;
import com.ssafy.miro.auth.domain.dto.UserToken;
import com.ssafy.miro.common.redis.RedisTokenService;
import com.ssafy.miro.user.application.response.UserInfo;
import com.ssafy.miro.user.domain.User;
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

    @PostMapping("/login/oauth2/code/google")
    public ResponseEntity<ApiResponse<UserTokenResponse>> oauth2Login(@RequestBody TokenCodeRequest tokenCodeRequest, HttpServletResponse response) {
        UserToken userToken = userOAuthService.authenticateUser(tokenCodeRequest.code());
        return jwtProvider.sendToken(
                response,
                userToken.getAccessToken(),
                userToken.getRefreshToken(),
                userToken.getUserInfo());
    }

    @PostMapping("/user-info")
    public ResponseEntity<ApiResponse<UserInfo>> userInfoByJwt(@Auth User user) {
        return ResponseEntity.ok().body(ApiResponse.of(SuccessCode.AUTH_SUCCESS, UserInfo.of(user)));
    }

}