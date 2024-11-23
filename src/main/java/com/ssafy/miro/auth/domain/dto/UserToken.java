package com.ssafy.miro.auth.domain.dto;

import com.ssafy.miro.user.application.response.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserToken {
    private Long id;
    private String accessToken;
    private String refreshToken;
    private UserInfo userInfo;
}
