package com.ssafy.miro.user.domain.dto;

import lombok.Getter;

@Getter
public class UserProfileDto {
    private String authId;
    private String nickname;
    private String email;


    public UserProfileDto(String authId, String email, String nickname) {
        this.authId = authId;
        this.email = email;
        this.nickname = nickname;
    }
}
