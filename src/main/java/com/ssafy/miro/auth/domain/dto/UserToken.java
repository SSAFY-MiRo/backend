package com.ssafy.miro.auth.domain.dto;

import com.ssafy.miro.user.application.response.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public record UserToken (
    Long id,
    String accessToken,
    String refreshToken,
    UserInfo userInfo
){}
