package com.ssafy.miro.user.presentation;

import com.ssafy.miro.common.ApiResponse;
import com.ssafy.miro.common.jwt.JwtProvider;
import com.ssafy.miro.user.application.Auth;
import com.ssafy.miro.user.application.UserOAuthService;
import com.ssafy.miro.user.application.response.UserTokenResponse;
import com.ssafy.miro.user.domain.User;
import com.ssafy.miro.user.domain.dto.AuthTokenDto;
import com.ssafy.miro.user.domain.dto.LoggedInUser;
import com.ssafy.miro.user.domain.dto.UserProfileDto;
import com.ssafy.miro.user.domain.dto.UserToken;
import com.ssafy.miro.user.domain.repository.UserOAuthRepository;
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
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserOAuthController {
    private final UserOAuthRepository userOAuthRepository;
    private final UserOAuthService userOAuthService;
    private final JwtProvider jwtProvider;

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect(userOAuthService.getUrl());
    }

    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<ApiResponse<UserTokenResponse>> oauth2Login(String code, HttpServletResponse response) throws IOException {
        AuthTokenDto tokenDto = userOAuthService.getToken(code);
        log.info("accessToken = {}", tokenDto);
        UserProfileDto userProfile = userOAuthService.getUserProfile(tokenDto);
        LoggedInUser loggedInUser = userOAuthService.registerUser(userProfile);


        log.info("loginUser = {}", loggedInUser);
        UserToken userToken = jwtProvider.generateAuthToken(loggedInUser.getId());

        log.info("userToken = {}", userToken);

        return jwtProvider.sendToken(response, userToken.getAccessToken(), userToken.getRefreshToken());
    }

    @GetMapping("parseTest")
    public void parseTest(@Auth User user) {
        log.info("user = {}", user);
    }

}