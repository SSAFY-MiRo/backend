package com.ssafy.miro.article.presentation.request;

import com.ssafy.miro.article.domain.ArticleCategory;
import jakarta.validation.constraints.NotBlank;

public record ArticleRequest(
        @NotBlank String title,
        @NotBlank String content,
        ArticleCategory category
) {
}
