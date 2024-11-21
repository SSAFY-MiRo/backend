package com.ssafy.miro.user.domain.dto;


public record AuthTokenDto(String accessToken, String code) {

    public static AuthTokenDto of(final String accessToken, final String code) {
        return new AuthTokenDto(accessToken, code);
    }
}
