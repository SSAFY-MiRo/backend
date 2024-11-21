package com.ssafy.miro.user.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record UserCheckPwdRequest(
        @NotBlank String password
) {

}
