package com.ssafy.miro.board.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record BoardRequest(
        @NotBlank String title,
        @NotBlank String content
) {
}
