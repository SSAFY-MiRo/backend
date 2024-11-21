package com.ssafy.miro.article.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.miro.article.domain.Article;

import java.time.LocalDateTime;

public record ArticleItems(
        Long id,
//        String nick,
        String title,
        Long view,
        @JsonFormat(pattern = "yyyy.mm.dd")
        LocalDateTime createdAt

) {
    public static ArticleItems of(Article article) {
        return new ArticleItems(article.getId(), article.getTitle(), article.getView(), article.getCreatedAt());
    }
}
