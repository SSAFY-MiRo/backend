package com.ssafy.miro.auth.application;

import com.ssafy.miro.common.jwt.JwtProvider;
import com.ssafy.miro.user.application.UserService;
import com.ssafy.miro.user.domain.User;
import com.ssafy.miro.auth.domain.dto.UserToken;
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

    @Value("${oauth2.login-url}")
    private String loginUrl;
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

    public String getUrl() {
        return loginUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri
                + "&response_type=code&scope=email profile";
    }

    public String getToken(String code) {
        WebClient webClient = WebClient
                .builder()
                .baseUrl("https://oauth2.googleapis.com")
                .build();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("code", code);
        requestBody.put("client_id", clientId);
        requestBody.put("client_secret", clientSecret);
        requestBody.put("redirect_uri", redirectUri);
        requestBody.put("grant_type", "authorization_code");
        Map<String, Object> response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/token")
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

    public UserCreateRequest getUserProfile(String accessToken) {
        WebClient webClient = WebClient
                .builder()
                .baseUrl("https://www.googleapis.com")
                .build();

        Map<String, Object> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("userinfo/v2/me")
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

    public UserToken getUserToken(String accessToken) {
        UserCreateRequest userProfile = getUserProfile(accessToken);
        Long id = userService.findByEmail(userProfile.email())
                .map(User::getId)  // if user exists
                .orElseGet(() -> userService.createUser(true, userProfile));

        return jwtProvider.generateAuthToken(id);
    }
}
