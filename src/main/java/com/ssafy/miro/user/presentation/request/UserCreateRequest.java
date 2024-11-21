package com.ssafy.miro.user.presentation.request;

import com.ssafy.miro.user.domain.User;
import com.ssafy.miro.user.domain.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequest(
        String authId,
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Invalid email format")
        String email,
        @NotBlank String password,
        @NotBlank String nickName
) {
    public User toUser() {
        return new User(this.authId, this.email, this.password, this.nickName, UserType.USER, "/" );
    }
}
