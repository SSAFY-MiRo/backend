package com.ssafy.miro.auth.application;

import com.ssafy.miro.common.jwt.JwtProvider;
import com.ssafy.miro.common.redis.RedisTokenService;
import com.ssafy.miro.user.application.UserService;
import com.ssafy.miro.user.domain.User;
import com.ssafy.miro.auth.domain.dto.UserToken;
import com.ssafy.miro.user.domain.UserType;
import com.ssafy.miro.user.presentation.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserOAuthService {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final RedisTokenService redisTokenService;

    @Value("${oauth2.client-id}")
    private String clientId;
    @Value("${oauth2.client-secret}")
    private String clientSecret;
    @Value("${oauth2.redirect-uri}")
    private String redirectUri;
    @Value("${oauth2.token-uri}")
    private String tokenUri;
    @Value("${oauth2.user-info-uri}")
    private String userInfoUri;

    public UserToken authenticateUser(String code) {
        String token = getOAuthToken(code);
        return getUserToken(token);
    }

    private String getOAuthToken(String code) {
        WebClient webClient = WebClient
                .builder()
                .baseUrl(tokenUri)
                .build();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("code", code);
        requestBody.put("client_id", clientId);
        requestBody.put("client_secret", clientSecret);
        requestBody.put("redirect_uri", redirectUri);
        requestBody.put("grant_type", "authorization_code");

        Map<String, Object> response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("code", code)
                        .build())
                .header(HttpHeaders.ACCEPT, "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        if (response == null) return null;
        return (String) response.get("access_token");
    }

    private UserCreateRequest getUserProfile(String accessToken) {
        WebClient webClient = WebClient
                .builder()
                .baseUrl(userInfoUri)
                .build();

        Map<String, Object> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("access_token", accessToken)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
        if (response == null) return null;
        String email = (String) response.get("email");
        String name = (String) response.get("name");

        return new UserCreateRequest(email, UUID.randomUUID().toString(), name);
    }

    private UserToken getUserToken(String accessToken) {
        UserCreateRequest userProfile = getUserProfile(accessToken);
        Long id = userService.findByEmail(userProfile.email())
                .map(User::getId)  // if user exists
                .orElseGet(() -> userService.createUser(true, userProfile, UserType.USER));

        return jwtProvider.generateAuthToken(id, userService.findByEmail(userProfile.email()).get());
    }
}
