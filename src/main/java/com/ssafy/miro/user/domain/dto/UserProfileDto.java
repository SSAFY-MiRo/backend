package com.ssafy.miro.user.domain.dto;

import lombok.Getter;

@Getter
public class UserProfileDto {
    private String nickname;
    private String email;


    public UserProfileDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
