package com.ssafy.miro.user.application.response;


import com.ssafy.miro.auth.domain.dto.UserToken;
import com.ssafy.miro.user.domain.User;

public record UserInfo(
    Boolean isAuth,
    Long userId,
    String nickname,
    String imageUrl
) {
    public static UserInfo of(User user) {
        return new UserInfo(user.getIsOAuth(), user.getId(), user.getNickname(), user.getProfileImage());
    }
}
