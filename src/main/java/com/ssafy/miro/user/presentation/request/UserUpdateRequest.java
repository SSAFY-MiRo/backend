package com.ssafy.miro.user.presentation.request;

public record UserUpdateRequest(
        String nickname,
        String password,
        String image
) {
}
