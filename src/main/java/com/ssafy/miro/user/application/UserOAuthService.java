package com.ssafy.miro.user.application;

import com.ssafy.miro.user.domain.dto.LoggedInUser;
import com.ssafy.miro.user.domain.User;
import com.ssafy.miro.user.domain.dto.UserProfileDto;
import com.ssafy.miro.user.domain.UserType;
import com.ssafy.miro.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserOAuthService {
    private final UserRepository userRepository;

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

        log.info("response = {}", requestBody);
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

    public UserProfileDto getUserProfile(String accessToken) {
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

        return new UserProfileDto(email, name);
    }

    public LoggedInUser registerUser(UserProfileDto userProfile) {
        String email = userProfile.getEmail();
        Optional<User> signedUser = userRepository.findByEmail(email);
        if (signedUser.isPresent()) {
            return new LoggedInUser(signedUser.get());
        }

        User user = User.builder()
                .email(userProfile.getEmail())
                .nickname(userProfile.getNickname())
                .password(UUID.randomUUID().toString())
                .userType(UserType.USER)
                .profileImage("123123")
                .build();

        userRepository.save(user);

        return new LoggedInUser(user);
    }
}
