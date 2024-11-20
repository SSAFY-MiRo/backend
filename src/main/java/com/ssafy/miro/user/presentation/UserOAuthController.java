package com.ssafy.miro.user.presentation;

import com.ssafy.miro.common.jwt.JwtUtil;
import com.ssafy.miro.user.application.UserOAuthService;
import com.ssafy.miro.user.domain.dto.LoggedInUser;
import com.ssafy.miro.user.domain.dto.UserProfileDto;
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
    private final JwtUtil jwtUtil;

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect(userOAuthService.getUrl());
    }

    @GetMapping("/login/oauth2/code/google")
    public ResponseEntity<Object> oauth2Login(String code) {
        String accessToken = userOAuthService.getToken(code);
        UserProfileDto userProfile = userOAuthService.getUserProfile(accessToken);
        LoggedInUser loggedInUser = userOAuthService.registerUser(userProfile);

        log.info("loggedInUser: {}", loggedInUser);
//        String token = jwtUtil.createToken(loggedInUser.getEmail(),
//                loggedInUser.getNickname(), Instant.now().toEpochMilli());


        return ResponseEntity.ok(new Object());
    }

}