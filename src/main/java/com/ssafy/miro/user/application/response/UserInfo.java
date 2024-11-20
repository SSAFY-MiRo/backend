package com.ssafy.miro.user.application.response;


import com.ssafy.miro.user.domain.User;

public record UserInfo(
    Boolean isAuth,
    Long userId,
    String nickname,
    String imageUrl
) {
    public static UserInfo of(User user){
        boolean isAuth = user.getAuthId() != null;
        return new UserInfo(isAuth, user.getId(), user.getNickname(), user.getProfileImage());
    }
}
