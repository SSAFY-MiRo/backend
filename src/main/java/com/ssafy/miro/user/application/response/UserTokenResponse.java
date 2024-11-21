package com.ssafy.miro.user.application.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserTokenResponse {
    private final String accessToken;
}
