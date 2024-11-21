package com.ssafy.miro.auth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserToken {
    private String accessToken;
    private String refreshToken;
}
