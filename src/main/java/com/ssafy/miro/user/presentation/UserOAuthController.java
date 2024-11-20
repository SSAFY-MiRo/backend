package com.ssafy.miro.user.presentation;

import com.ssafy.miro.common.jwt.JwtProvider;
import com.ssafy.miro.user.application.Auth;
import com.ssafy.miro.user.application.UserOAuthService;
import com.ssafy.miro.user.domain.User;
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
    public ResponseEntity<Object> oauth2Login(String code) {
        String accessToken = userOAuthService.getToken(code);
        UserProfileDto userProfile = userOAuthService.getUserProfile(accessToken);
        LoggedInUser loggedInUser = userOAuthService.registerUser(userProfile);


        log.info("loginUser = {}", loggedInUser);
        UserToken userToken = jwtProvider.generateAuthToken(loggedInUser.getId());

        log.info("userToken = {}", userToken);


        return ResponseEntity.ok(new Object());
    }

    @GetMapping("parseTest")
    public void parseTest(@Auth User user) throws IOException {
        Long id = jwtProvider.getId("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiaWF0IjoxNzMyMTE3MTEyLCJleHAiOjE3MzI5ODExMTJ9.EM5CPwyazau5LzJJZKrJWjpGVXWbDvucwkKWek09cGA");
        log.info("user = {}", user);
        log.info("id = {}", id);
    }

}