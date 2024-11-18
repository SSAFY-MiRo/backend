package com.ssafy.miro.article.application.response;

import com.ssafy.miro.article.domain.Article;

public record ArticleItems(
        Long id,
        String title,
        String content,
        Long view
) {
    public static ArticleItems of(Article article) {
        return new ArticleItems(article.getId(), article.getTitle(), article.getContent(), article.getView());
    }
}
