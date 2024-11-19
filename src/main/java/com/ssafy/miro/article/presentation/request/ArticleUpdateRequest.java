package com.ssafy.miro.article.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record ArticleUpdateRequest(
        @NotBlank String title,
        @NotBlank String content
) {
}
