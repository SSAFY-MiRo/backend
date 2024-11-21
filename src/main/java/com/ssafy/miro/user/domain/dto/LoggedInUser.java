package com.ssafy.miro.user.domain.dto;

import com.ssafy.miro.user.domain.User;
import com.ssafy.miro.user.domain.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LoggedInUser {
    private Long id;
    private String authId;
    private String nickname;
    private String email;
    private UserType userType;


    public LoggedInUser(User user) {
        this.id = user.getId();
        this.authId = user.getAuthId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.userType = user.getUserType();
    }
}
