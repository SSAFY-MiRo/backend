package com.ssafy.miro.user.presentation.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record UserUpdateRequest(
        @NotBlank String nickname,
        @NotBlank String password,
        @NotBlank String passwordConfirm
) {
}
